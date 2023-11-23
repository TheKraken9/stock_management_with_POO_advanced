package com.project.stock2.model;

import com.project.stock2.util.connecting.Connecting;

import java.util.ArrayList;

public class EtatDeStock {
    private String id_mouvement;
    private String id_article;
    private String article;
    private String id_magasin;
    private String magasin;
    private double quantite;
    private double prix;
    private String date;
    private double total;
    private String unite;

    public EtatDeStock() {
    }

    public EtatDeStock(String id_mouvement, String id_article, String id_magasin, int quantite, double prix, String date) {
        this.id_mouvement = id_mouvement;
        this.id_article = id_article;
        this.id_magasin = id_magasin;
        this.quantite = quantite;
        this.prix = prix;
        this.date = date;
    }

    public String getId_mouvement() {
        return id_mouvement;
    }

    public void setId_mouvement(String id_mouvement) {
        this.id_mouvement = id_mouvement;
    }

    public String getId_article() {
        return id_article;
    }

    public void setId_article(String id_article) {
        this.id_article = id_article;
    }

    public String getId_magasin() {
        return id_magasin;
    }

    public void setId_magasin(String id_magasin) {
        this.id_magasin = id_magasin;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String id_article) throws Exception{
        if(id_article == null){
            this.article = "";
            return;
        }
        Article article = new Article();
        String nom_article = null;
        article.setId(id_article);
        try{
            nom_article = article.getNomArticle();
        }catch (Exception e){
            throw e;
        }
        this.article = nom_article;
    }

    public String getMagasin() {
        return magasin;
    }

    public void setMagasin(String id_magasin) throws Exception{
        if(id_magasin == null){
            this.magasin = "";
            return;
        }
        Magasin magasin = new Magasin();
        String nom_magasin = null;
        magasin.setId(id_magasin);
        try{
            nom_magasin = magasin.getNomMagasin();
        }catch (Exception e){
            throw e;
        }
        this.magasin = nom_magasin;
    }

    public ArrayList<EtatDeStock> getEtatDeStocks(String id_article, String id_magasin, String date1) throws Exception {
        boolean close_connection = false;
        String sql = null;
        ArrayList<EtatDeStock> etatDeStocks = new ArrayList<>();
        java.sql.Connection connection = Connecting.getConnection("postgres");
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "SELECT * from calculer_reste(?, ?, ?)";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, java.sql.Date.valueOf(date1));
            preparedStatement.setString(2, id_article);
            preparedStatement.setString(3, id_magasin);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                EtatDeStock etatDeStock = new EtatDeStock();
                etatDeStock.setId_mouvement(resultSet.getString("id"));
                etatDeStock.setId_article(resultSet.getString("id_article"));
                etatDeStock.setArticle(resultSet.getString("id_article"));
                etatDeStock.setUnite(resultSet.getString("unite"));
                etatDeStock.setId_magasin(resultSet.getString("id_magasin"));
                etatDeStock.setMagasin(resultSet.getString("id_magasin"));
                etatDeStock.setQuantite(resultSet.getInt("reste"));
                etatDeStock.setPrix(resultSet.getDouble("prix_unitaire"));
                etatDeStock.setTotal(resultSet.getDouble("total"));
                etatDeStock.setDate(date1);
                etatDeStocks.add(etatDeStock);
            }
            EtatDeStock total_etat_de_stock = new EtatDeStock();
            double reste_total = 0;
            double prix_unitaire_total = 0;
            double total_total = 0;
            for (EtatDeStock etatDeStock: etatDeStocks) {
                prix_unitaire_total += etatDeStock.getPrix();
                reste_total += etatDeStock.getQuantite();
                total_total += etatDeStock.getTotal();
            }
            if(etatDeStocks.isEmpty()){
                total_etat_de_stock.setUnite("Aucune");
            }else{
                total_etat_de_stock.setUnite(etatDeStocks.get(0).getUnite());
            }
            total_etat_de_stock.setTotal(total_total);
            total_etat_de_stock.setPrix(total_total/reste_total);
            total_etat_de_stock.setQuantite(reste_total);
            total_etat_de_stock.setDate(date1);
            total_etat_de_stock.setId_article(id_article);
            total_etat_de_stock.setArticle(null);
            total_etat_de_stock.setId_magasin(id_magasin);
            total_etat_de_stock.setMagasin(null);
            etatDeStocks.add(total_etat_de_stock);
        }catch (Exception e){
            throw e;
        }finally {
            if(close_connection){
                connection.close();
            }
        }
        return etatDeStocks;
    }

    public static void main(String[] args) throws Exception {
        /*EtatDeStock etatDeStock = new EtatDeStock();
        ArrayList<EtatDeStock> etatDeStocks = etatDeStock.getEtatDeStocks("ART1", "MAG1", "2023-01-01");
        for (EtatDeStock etatDeStock1: etatDeStocks) {
            System.out.println(etatDeStock1.getId_mouvement());
            System.out.println(etatDeStock1.getId_article());
            System.out.println(etatDeStock1.getId_magasin());
            System.out.println(etatDeStock1.getQuantite());
            System.out.println(etatDeStock1.getPrix());
            System.out.println(etatDeStock1.getTotal());
            System.out.println(etatDeStock1.getDate());
        }*/
        EtatDeStock etatDeStock = new EtatDeStock();
        /*etatDeStock.setArticle("ART4");
        System.out.println(etatDeStock.getArticle());
        etatDeStock.setMagasin("MAG1");
        System.out.println(etatDeStock.getMagasin());*/
        ArrayList<EtatDeStock> etatDeStocks = etatDeStock.getEtatDeStocks("RART1", "MAG1", "2023-01-01");
    }
}
