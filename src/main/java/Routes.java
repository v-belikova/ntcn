public final class Routes {

    // pages
    public String loginPage = "https://news-feed.dunice-testing.com/loign" + System.getenv("INTERNSHIP_NGROK");
    public String registrationPage = "https://news-feed.dunice-testing.com/registration";

    // requests
    public String postRegistration = "https://news-feed.dunice-testing.com/api/v1/auth/register";
    public String postLogin = "https://news-feed.dunice-testing.com/api/v1/auth/login";
    public String getUserInfo = "https://news-feed.dunice-testing.com/api/v1/user";
    public String getUserInfoId = "https://news-feed.dunice-testing.com/api/v1/user/";
    public String updateUserInfo = "https://news-feed.dunice-testing.com/api/v1/user";
    public String deleteUser = "https://news-feed.dunice-testing.com/api/v1/user";

    public String createNews = "https://news-feed.dunice-testing.com/api/v1/news";
    public String getNews = "https://news-feed.dunice-testing.com/api/v1/news?page=1&perPage=3";
    public String getOneNews = "https://news-feed.dunice-testing.com/api/v1/news/find";
    public String getOneNews1 = "https://news-feed.dunice-testing.com/api/v1/news/user/{userId}";
    public String updateNews = "https://news-feed.dunice-testing.com/api/v1/news/";
    public String deleteNews = "https://news-feed.dunice-testing.com/api/v1/news/";


    public String fileControl = "https://news-feed.dunice-testing.com/api/v1/file/uploadFile";
    public String getFileControl = "https://news-feed.dunice-testing.com/api/v1/file/{fileName}";
}

