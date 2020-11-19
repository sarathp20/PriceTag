package com.example.pricetag;

public class Product {

    private String title;
    // private String shortdesc;
    private Float rating;
    private Float price;
    // private String image;
    private String site;
    private String link;
    public Product(String title/* ,String shortdesc */,Float rating, Float price /*,String image*/,String site,String link) {
        this.title = title;
        //    this.shortdesc = shortdesc;
        this.price = price;
        //   this.image = image;
        this.site = site;
        this.link = link;
        this.rating = rating;
    }

    public String getTitle() { return title; }

    //   public String getShortdesc() {
    //       return shortdesc;
    //     }

    public Float getRating() {
        return rating;
    }

    public Float getPrice() {
        return price;
    }

    /* public String getImage() { return image; } */
    public String getsite() { return site; }
    public String getlink() { return link; }
}

