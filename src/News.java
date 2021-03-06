public class News {

    String[] titleNews;
    String[] urlNews;
    int lastIndex;

    public News(int size) {
        titleNews = new String[size+1];
        urlNews = new String[size+1];
        lastIndex = -1;
    }

    public String push(String title, String url) {
        lastIndex++;

        if ((lastIndex + 1) >= titleNews.length) {
            return "Ошибка: Слишком много новостей %)";
        }

        titleNews[lastIndex] = title;
        urlNews[lastIndex] = url;

        return "Ok";
    }

    public void print(String category){
        System.out.println("("+(lastIndex+1)+") "+category);
        for(int i =0; i <= lastIndex; i++) {
            System.out.println(titleNews[i]);
            System.out.println("Подробности по ссылке: "+urlNews[i]);
        }
        System.out.println("");
        return;
    }

    public void clear() {
        this.lastIndex = -1;
    }
}
