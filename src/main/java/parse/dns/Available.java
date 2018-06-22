package parse.dns;

public class Available {
    //Код товара – уникальное поле, целое
    Integer code;
    //город магазина
    String city;
    //название магазина
    String shopName;
    //количество товара в наличии
    Integer count;
    //количество дней ожидания при заказе
    Integer waitingForOrderInDays;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getWaitingForOrderInDays() {
        return waitingForOrderInDays;
    }

    public void setWaitingForOrderInDays(Integer waitingForOrderInDays) {
        this.waitingForOrderInDays = waitingForOrderInDays;
    }
}
