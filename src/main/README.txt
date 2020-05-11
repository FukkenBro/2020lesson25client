    Setup your connection configuration in ApiService.java
     * private String ip = "127.0.0.1";
     * private String port = "";

    Use following query commands to access to the service's featurs:
     ApiService service = ApiService.getInstance() - initialize your service to begin work;
     * User user = new User(String name, int age) - create new user with auto-ID
     * service.create(User user) - allows you store your user on server, doesn't allow users with duplicate ID
     * service.read(int targetUserId) - returns json of user object if user with given ID exists on server
     * service.update(User user) - allows you to update the user if user with given ID exists on server
     * service.delete(int targetUserId) - allows you to delete the user if user with given ID exists on server
     * service.getAllUsers() - returns json of ArrayList<User> object with all users stored on server