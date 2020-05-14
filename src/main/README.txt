    Setup your connection configuration in ApiService.java
     * private String ip = "127.0.0.1";
     * private String port = "";

    Use following query commands to access to the service's features:
     ApiService service = ApiService.getInstance() - initialize your service to begin work;
     * Image image = new Image(String base64) - create new image with auto-ID
     * service.createFromFile() - allows you to chose the image with file dialog and immediately upload it to the server
     * service.create(Image image) - allows you store your image on server, doesn't allow images with duplicate ID
     * service.read(int targetImageId) - returns base64 of image if image with given ID exists on server
     * service.update(Image image) - allows you to update the image if image with given ID exists on server
     * service.delete(int targetImageId) - allows you to delete the image if image with given ID exists on server
     * service.deleteAll() - wipes all images on server with reset of IDs
     * service.getAllImages() - returns json of ArrayList<Image> object with all images stored on server
     * your images are available from browser via http://"server-ip":"port"/my-api/images/show/"imageId"