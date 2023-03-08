package services;

public  class UserSession {
    public static UserSession CURRENT_USER;
    private static AuthResponseDTO user_LoggedIn;

    private UserSession(AuthResponseDTO user_LoggedIn ) {
        this.user_LoggedIn = user_LoggedIn;
    }

    public static UserSession getSameInstance(AuthResponseDTO user_LoggedIn) {
        if(CURRENT_USER == null) {
            CURRENT_USER = new UserSession(user_LoggedIn);
        }
        return CURRENT_USER;
    }

    public static AuthResponseDTO getUser_LoggedIn() {
        return user_LoggedIn;
    }

    public void Logout() {
        this.user_LoggedIn = null;
        CURRENT_USER = null;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "_this_user='" + user_LoggedIn + '\'' +
                ", privileges="  +
                '}';
    }
}
