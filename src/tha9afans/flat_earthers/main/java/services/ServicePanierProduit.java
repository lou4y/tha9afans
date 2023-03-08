package services;

import entities.Panier;
import entities.PanierProduit;
import entities.Personne;
import entities.Produit;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePanierProduit implements IService<PanierProduit> {
    Connection cnx = DataSource.getInstance().getCnx();
    ServicePanier sp = new ServicePanier();
    ServiceProduit sp2 = new ServiceProduit();

    ServicePersonne sp3 = new ServicePersonne();

    @Override
    public void ajouter(PanierProduit p) {
        try {
            String req = "INSERT INTO panierproduit( `id_panier `,`id_produit`,`quantity` ) VALUES (?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, p.getPanier().getId());
            ps.setInt(2, p.getProduit().getId());
            ps.setInt(3, p.getQuantite());

            ps.executeUpdate();
            System.out.println("Commande created ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM panierproduit WHERE id_produit= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("produit deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

    @Override
    public void modifier(PanierProduit p) {
        try {
            String req = "UPDATE panierproduit SET `id_panier`=?,`id_produit`=?,`quantity`=? WHERE id_produit=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, p.getPanier().getId());
            ps.setInt(2, p.getProduit().getId());
            ps.setInt(3, p.getQuantite());
            ps.setInt(4, p.getProduit().getId());
            ps.executeUpdate();
            System.out.println("produit updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<PanierProduit> getAll() {

        List<PanierProduit> list = new ArrayList<>();
        try {
            String req = "Select * from panierproduit";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                PanierProduit e = new PanierProduit(
                        sp.getOneById(rs.getInt(1)),
                        sp2.getOneById(rs.getInt(2)),
                        rs.getInt(3));

                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;

    }

    @Override
    public PanierProduit getOneById(int id) {
        PanierProduit result = null;
        try {
            String req = "SELECT * FROM panierproduit WHERE id_=" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                result = new PanierProduit(
                        sp.getOneById(rs.getInt(1)),
                        sp2.getOneById(rs.getInt(2)),
                        rs.getInt(3));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    /* ajouter */

    public void ajouterquantite(int id) {
        try {
            String req = "UPDATE panierproduit SET quantity=quantity+1 WHERE id_produit=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("quantite updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

    public void supprimerquantite(int id) {
        try {
            String req = "UPDATE panierproduit SET quantity=quantity-1 WHERE id_produit=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("quantite updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

    // Si la quantité est égale à 1, on supprime complètement la ligne
    // correspondante dans la table panier
    // Si la quantité est supérieure à 1, on décrémente la quantité dans la table
    // panier

    public void minQuantite(int id) {
        try {
            String selectQuery = "SELECT quantity FROM panierproduit WHERE id_produit = ?";
            PreparedStatement selectStatement = cnx.prepareStatement(selectQuery);
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int quantity = resultSet.getInt("quantity");

                if (quantity > 1) {
                    String updateQuery = "UPDATE panierproduit SET quantity = quantity - 1 WHERE id_produit = ?";
                    PreparedStatement updateStatement = cnx.prepareStatement(updateQuery);
                    updateStatement.setInt(1, id);
                    updateStatement.executeUpdate();
                    System.out.println("Quantité mise à jour");
                } else {
                    String deleteQuery = "DELETE FROM panierproduit WHERE id_produit = ?";
                    PreparedStatement deleteStatement = cnx.prepareStatement(deleteQuery);
                    deleteStatement.setInt(1, id);
                    deleteStatement.executeUpdate();
                    System.out.println("Produit supprimé du panier");
                }
            } else {
                System.out.println("Aucun produit avec l'ID " + id + " n'a été trouvé dans le panier");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void ajouterProduitPanier(Produit produit, Personne personne) {
        try {

            PreparedStatement ps1 = cnx.prepareStatement("SELECT * FROM panier WHERE id_user = ?");
            ps1.setInt(1, personne.getId());
            ResultSet rs = ps1.executeQuery();

            Panier panier;
            if (rs.next()) {
                int id_panier = rs.getInt(1);
                panier = new Panier(id_panier, 0, personne);
            } else {
                PreparedStatement ps2 = cnx.prepareStatement("INSERT INTO panier (id_user) VALUES (?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps2.setInt(1, personne.getId());
                ps2.executeUpdate();
                rs = ps2.getGeneratedKeys();
                rs.next();
                int id_panier = rs.getInt(1);
                panier = new Panier(id_panier, 0, personne);
            }
            PreparedStatement ps3 = cnx
                    .prepareStatement("SELECT * FROM panierproduit WHERE id_panier = ? AND id_produit = ?");
            ps3.setInt(1, panier.getId());
            ps3.setInt(2, produit.getId());
            rs = ps3.executeQuery();

            if (rs.next()) {
                PreparedStatement ps4 = cnx.prepareStatement(
                        "UPDATE panierproduit SET quantity = quantity + 1 WHERE id_panier = ? AND id_produit = ?");
                ps4.setInt(1, panier.getId());
                ps4.setInt(2, produit.getId());
                ps4.executeUpdate();
            } else {
                PreparedStatement ps5 = cnx.prepareStatement(
                        "INSERT INTO panierproduit (id_panier, id_produit, quantity) VALUES (?, ?, ?)");
                ps5.setInt(1, panier.getId());
                ps5.setInt(2, produit.getId());
                ps5.setInt(3, 1);
                ps5.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void supprimerLivrePanier(Produit produit) {
        try {
            // Récupération de la quantité actuelle du livre dans le panier
            String selectQuery = "SELECT quantity FROM panierproduit WHERE id_produit = ?";
            PreparedStatement selectStmt = cnx.prepareStatement(selectQuery);
            selectStmt.setInt(1, produit.getId());
            ResultSet rs = selectStmt.executeQuery();
            int quantiteActuelle = 0;
            if (rs.next()) {
                quantiteActuelle = rs.getInt("quantity");
            }
            if (quantiteActuelle == 1) {
                // Si la quantité est égale à 1, on supprime complètement la ligne
                // correspondante dans la table panier
                String deleteQuery = "DELETE FROM panierproduit WHERE id_produit = ?";
                PreparedStatement deleteStmt = cnx.prepareStatement(deleteQuery);
                deleteStmt.setInt(1, produit.getId());
                deleteStmt.executeUpdate();
            } else if (quantiteActuelle > 1) {
                // Si la quantité est supérieure à 1, on décrémente la quantité dans la table
                // panier
                String updateQuery = "UPDATE panierproduit SET quantity = quantity - 1 WHERE id_produit = ?";
                PreparedStatement updateStmt = cnx.prepareStatement(updateQuery);
                updateStmt.setInt(1, produit.getId());
                updateStmt.executeUpdate();
            }

            System.out.println("produit supprimé du panier avec succès !");

        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression du livre du panier: " + ex.getMessage());
        }
    }

    ServicePanier servicePanier = new ServicePanier();
    ServiceProduit serviceProduit = new ServiceProduit();

    public List<PanierProduit> getproduitdanspanier(Panier panier) {
        List<PanierProduit> list = new ArrayList<>();
        try {
            PreparedStatement ps = cnx.prepareStatement("SELECT * FROM panierproduit WHERE id_panier = ?");
            ps.setInt(1, panier.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PanierProduit pp = new PanierProduit(
                        servicePanier.getOneById(rs.getInt(1)),
                        serviceProduit.getOneById(rs.getInt(2)),
                        rs.getInt(3));

                list.add(pp);
            }
        } catch (SQLException ex) {

            System.out.println("Erreur lors de la récupération des produits dans le panier : " + ex.getMessage());
        }
        return list;

    }

    /*
     * public List<Livre> afficherLivresDansPanier(int id_panier) {
     * List<Livre> var = new ArrayList();
     * try {
     * PreparedStatement ps = cnx.
     * prepareStatement("SELECT livre.titre, livre.prix FROM panierlivre JOIN livre ON panierlivre.id_livre = livre.id_livre WHERE panierlivre.id_panier = ?"
     * );
     * ps.setInt(1, id_panier);
     * ResultSet rs = ps.executeQuery();
     * 
     * while (rs.next()) {
     * String titre = rs.getString("titre");
     * float prix = rs.getFloat("prix");
     * Livre l = new Livre(titre,prix);
     * var.add(l);
     * }
     * 
     * } catch (SQLException ex) {
     * System.out.
     * println("Erreur lors de la récupération des livres dans le panier : " +
     * ex.getMessage());
     * }
     * return var;
     * }
     */

}
