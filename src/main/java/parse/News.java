package parse;

import util.MySQLInsertNews;

import java.sql.SQLException;

public class News {

    String[] titleNews;
    String[] urlNews;
    int lastIndex;

    public News(int size) {
        titleNews = new String[size+1];
        urlNews = new String[size+1];
        lastIndex = -1;
    }

    public String push(String title, String url, String typeOfNews) throws SQLException {
        //сохранять в базу данных

        MySQLInsertNews.insert(title,url,typeOfNews);
        lastIndex++;

        if ((lastIndex + 1) >= titleNews.length) {
            return "Ошибка: Слишком много новостей %)";
        }

        titleNews[lastIndex] = title;
        urlNews[lastIndex] = url;

        return "Ok";
    }

    public void print(String category) throws SQLException {

        System.out.println("("+(lastIndex+1)+") "+category);
        for(int i =0; i <= lastIndex; i++) {
            System.out.println((i+1)+") "+titleNews[i]);
            System.out.println("Подробности по ссылке: "+urlNews[i]);
        }
        System.out.println("");
        return;
    }



}
