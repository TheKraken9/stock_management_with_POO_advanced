package com.project.stock2.model;

import com.project.stock2.util.connecting.Connecting;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

public class Article {
    private String id;
    private String libelle;
    private String id_type;

    public Article() {
    }

    public Article(String id, String libelle, String id_type) {
        this.id = id;
        this.libelle = libelle;
        this.id_type = id_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Exception {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getNomArticle() throws Exception {
        String sql = "SELECT libelle FROM article WHERE id like ? limit 1";
        try{
            java.sql.PreparedStatement preparedStatement = Connecting.getConnection("postgres").prepareStatement(sql);
            preparedStatement.setString(1, this.id);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString("libelle");
            }
        }catch (Exception e){
            throw e;
        }
        return null;
    }

    public Article articleExists(Connection connection) throws Exception{
        boolean close_connection = false;
        String sql = null;
        Article article = new Article();
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "SELECT * FROM article WHERE id like ? limit 1";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.id);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                System.out.println(resultSet.getString("id"));
                article.setId(resultSet.getString("id"));
                article.setLibelle(resultSet.getString("libelle"));
                article.setId_type(resultSet.getString("id_type"));
            }
            if(article.getId() == null){
                throw new Exception("Article not found");
            }
        }catch (Exception e){
            throw e;
        }finally {
            if(close_connection){
                connection.close();
            }
        }
        return article;
    }

    public boolean articleExistsInMagasinOnDate(Connection connection, String id_magasin, Date dateExistence) throws Exception {
        boolean close_connection = false;
        String sql = null;
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "SELECT * FROM mouvement WHERE id_article like ? AND id_magasin like ? AND date_mouvement <= ?";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.id);
            preparedStatement.setString(2, id_magasin);
            preparedStatement.setDate(3, dateExistence);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            if(this.id == null){
                throw new Exception("Article not found");
            }
        }catch (Exception e){
            throw e;
        }
        if(close_connection){
            connection.close();
        }
        return false;
    }

    public boolean quantiteForArticleIsSufficient(Connection connection, String id_magasin, double quantite, Date date_mouvement) throws Exception {
        System.out.println("quantite = " + quantite);
        System.out.println("id " + this.id);
        boolean close_connection = false;
        String date = date_mouvement.toString();
        String sql = null;
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        try{
            EtatDeStock etatDeStock = new EtatDeStock();
            ArrayList<EtatDeStock> etatDeStocks = etatDeStock.getEtatDeStocks(this.id, id_magasin, date);
            double quantite_restante = 0;
            quantite_restante = etatDeStocks.get(etatDeStocks.size() - 1).getQuantite();
            System.out.println("qtt restante = " + quantite_restante);
            if(quantite_restante >= quantite){
                return true;
            }
        }catch (Exception e){
            throw new Exception("Error while checking if article exists in magasin");
        }
        if(close_connection){
            connection.close();
        }
        return false;
    }

    public ArrayList<Article> getArticles(Connection connection) throws Exception {
        boolean close_connection = false;
        String sql = null;
        ArrayList<Article> articles = new ArrayList<>();
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "SELECT * FROM article";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Article article = new Article();
                article.setId(resultSet.getString("id"));
                article.setLibelle(resultSet.getString("libelle"));
                article.setId_type(resultSet.getString("id_type"));
                articles.add(article);
            }
        }catch (Exception e){
            throw new Exception("Error while getting articles");
        }finally {
            if(close_connection){
                connection.close();
            }
        }
        return articles;
    }

    public boolean uniteEquivalenceExistsPourCeProduit(Connection connection, String id_unite_equivalence) throws Exception {
        boolean close_connection = false;
        String sql = null;
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "SELECT * FROM unite_quivalence WHERE id_article like ? AND id like ?";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.id);
            preparedStatement.setString(2, id_unite_equivalence);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            if(this.id == null){
                throw new Exception("Article not found");
            }
        }catch (Exception e){
            throw e;
        }
        if(close_connection){
            connection.close();
        }
        return false;
    }


    public static void main(String[] args) throws Exception {
        //Article article = new Article();
        //article.setId("ART1");
        //article.articleExists(null, "ART1");
        //boolean res = article.articleExistsInMagasinOnDate(null, "MAG1", Date.valueOf("2024-01-01"));
        //System.out.println(res);
        /*Mouvement mouvement = new Mouvement();
        try{
            Mouvement mvt = mouvement.getInfoDemande(null,"MED2");
            //if(action.equalsIgnoreCase("ok")){
                Article article = new Article().articleExists(null, mvt.getId_article());
                mouvement.dateAnterieure(null,mvt.getId_article(), mvt.getId_magasin(),mvt.getDate_mouvement());
                boolean exists = article.articleExistsInMagasinOnDate(null, mvt.getId_magasin(), mvt.getDate_mouvement());
                //mvt.insertMouvement(null);
                //}else if(action.equalsIgnoreCase("no")){
            System.out.println(exists);
            System.out.println(mvt.getId_article());
            System.out.println(mvt.getId());
            System.out.println(mvt.getId_magasin());
            System.out.println(mvt.getQuantite());
            System.out.println(mvt.getDate_mouvement());
            System.out.println(mvt.getId_article());
            //}
        }catch (Exception e){
            e.printStackTrace();
        }*/
        Article article = new Article();
        article.setId("RART1");
        boolean res = article.uniteEquivalenceExistsPourCeProduit(null, "UEQ5");
        System.out.println(res);
        //boolean res = article.quantiteForArticleIsSufficient(null, "MAG1", 1.0, Date.valueOf("2023-01-01"));
        //System.out.println(res);
        article.articleExists(null);
    }

}
