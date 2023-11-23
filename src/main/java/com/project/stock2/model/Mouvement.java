package com.project.stock2.model;

import com.project.stock2.util.connecting.Connecting;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

public class Mouvement {
    private String id;
    private String id_article;
    private String article;
    private String id_magasin;
    private String magasin;
    private double quantite;
    private Date date_mouvement;
    private double prix_unitaire;
    private String id_mouvement_parent;
    private int etat;
    private String id_demande;
    private String unite_equivalence;

    public Mouvement() {
    }

    public Mouvement(String id, String id_article, String id_magasin, double quantite, Date date_mouvement, double prix_unitaire, String id_mouvement_parent, int etat, String id_demande) {
        this.id = id;
        this.id_article = id_article;
        this.id_magasin = id_magasin;
        this.quantite = quantite;
        this.date_mouvement = date_mouvement;
        this.prix_unitaire = prix_unitaire;
        this.id_mouvement_parent = id_mouvement_parent;
        this.etat = etat;
        this.id_demande = id_demande;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        if(quantite < 0){
            quantite = 0;
        }
        this.quantite = quantite;
    }

    public Date getDate_mouvement() {
        return date_mouvement;
    }

    public void setDate_mouvement(Date date_mouvement) {
        if(date_mouvement == null){
            date_mouvement = new Date(System.currentTimeMillis());
        }
        this.date_mouvement = date_mouvement;
    }

    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(double prix_unitaire) {
        if(prix_unitaire < 0){
            prix_unitaire = 0;
        }
        this.prix_unitaire = prix_unitaire;
    }

    public String getId_mouvement_parent() {
        return id_mouvement_parent;
    }

    public void setId_mouvement_parent(String id_mouvement_parent) {
        this.id_mouvement_parent = id_mouvement_parent;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        if(etat < 0){
            etat = 0;
        }
        this.etat = etat;
    }

    public String getId_demande() {
        return id_demande;
    }

    public void setId_demande(String id_demande) {
        this.id_demande = id_demande;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getMagasin() {
        return magasin;
    }

    public void setMagasin(String magasin) {
        this.magasin = magasin;
    }

    public String getUnite_equivalence() {
        return unite_equivalence;
    }

    public void setUnite_equivalence(String unite_equivalence) {
        this.unite_equivalence = unite_equivalence;
    }

    public boolean dateAnterieure(Connection connection, String id_art, String id_mag, Date date_mvt) throws Exception{
        boolean ok = false;
        boolean close_connection = false;
        String sql = null;
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "SELECT * FROM mouvement WHERE id_article like ? AND id_magasin like ? AND id_mouvement_parent is not null order by id desc limit 1";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id_art);
            preparedStatement.setString(2, id_mag);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Date date = resultSet.getDate("date_mouvement");
                if(date.after(date_mvt)){
                    System.out.println("error date anterieure");
                    ok = true;
                }
            }
        }catch (Exception e){
            throw e;
        }finally {
            if(close_connection){
                connection.close();
            }
        }
        return ok;
    }

    public void insertDemande(Connection connection) throws Exception {
        boolean close_connection = false;
        String sql = null;
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "INSERT INTO mouvement_entre_demande(id, id_article, id_magasin, quantite, date_mouvement, etat, id_equivalence) VALUES(generer_primary_key('mouvement_entre_demande'), ?, ?, ?, ?, ?, ?)";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.id_article);
            preparedStatement.setString(2, this.id_magasin);
            preparedStatement.setDouble(3, this.quantite);
            preparedStatement.setDate(4, this.date_mouvement);
            preparedStatement.setInt(5, this.etat);
            preparedStatement.setString(6, this.unite_equivalence);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            throw e;
        }finally {
            if(close_connection){
                connection.close();
            }
        }
    }

    public void insertEntre(Connection connection) throws Exception {
        boolean close_connection = false;
        String sql = null;
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "select inserer_mouvement_en_entre(?, ?, ?, ?, ?, ?, null, 0, null)";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.id_article);
            preparedStatement.setString(2, this.unite_equivalence);
            preparedStatement.setDouble(3, this.quantite);
            preparedStatement.setString(4, this.id_magasin);
            preparedStatement.setDate(5, this.date_mouvement);
            preparedStatement.setDouble(6, this.prix_unitaire);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            throw e;
        }finally {
            if(close_connection){
                connection.close();
            }
        }
    }

    public ArrayList<Mouvement> getDemandes(Connection connection) throws Exception {
        boolean close_connection = false;
        String sql = null;
        ArrayList<Mouvement> mouvements = new ArrayList<>();
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "select mouvement_entre_demande.id as id, id_article, article.libelle as article, id_magasin, magasin.libelle as magasin, quantite, date_mouvement,etat from mouvement_entre_demande join article on article.id=mouvement_entre_demande.id_article join magasin on magasin.id=mouvement_entre_demande.id_magasin where etat=0";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Mouvement mouvement = new Mouvement();
                mouvement.setId(resultSet.getString("id"));
                mouvement.setId_article(resultSet.getString("id_article"));
                mouvement.setArticle(resultSet.getString("article"));
                mouvement.setId_magasin(resultSet.getString("id_magasin"));
                mouvement.setMagasin(resultSet.getString("magasin"));
                mouvement.setQuantite(resultSet.getDouble("quantite"));
                mouvement.setDate_mouvement(resultSet.getDate("date_mouvement"));
                mouvement.setEtat(resultSet.getInt("etat"));
                mouvements.add(mouvement);
            }
        }catch (Exception e){
            throw e;
        }finally {
            if(close_connection){
                connection.close();
            }
        }
        return mouvements;
    }

    public ArrayList<Mouvement> getListVrai(Connection connection) throws Exception {
        boolean close_connection = false;
        String sql = null;
        ArrayList<Mouvement> mouvements = new ArrayList<>();
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "select * from vraimouvement";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Mouvement mouvement = new Mouvement();
                mouvement.setId(resultSet.getString("id"));
                mouvement.setId_article(resultSet.getString("id_article"));
                mouvement.setId_magasin(resultSet.getString("id_magasin"));
                mouvement.setQuantite(resultSet.getDouble("quantite"));
                mouvement.setDate_mouvement(resultSet.getDate("date_mouvement"));
                mouvement.setPrix_unitaire(resultSet.getInt("prix"));
                mouvements.add(mouvement);
            }
        }catch (Exception e){
            throw e;
        }finally {
            if(close_connection){
                connection.close();
            }
        }
        return mouvements;
    }

    public Mouvement getInfoDemande(Connection connection) throws Exception {
        boolean close_connection = false;
        String sql = null;
        Mouvement mouvement = new Mouvement();
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "select mouvement_entre_demande.id as id, id_article, article.libelle as article, id_magasin, magasin.libelle as magasin, quantite, date_mouvement,etat from mouvement_entre_demande join article on article.id=mouvement_entre_demande.id_article join magasin on magasin.id=mouvement_entre_demande.id_magasin where etat=0 and mouvement_entre_demande.id=?";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.id_demande);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                mouvement.setId(resultSet.getString("id"));
                mouvement.setId_article(resultSet.getString("id_article"));
                mouvement.setArticle(resultSet.getString("article"));
                mouvement.setId_magasin(resultSet.getString("id_magasin"));
                mouvement.setMagasin(resultSet.getString("magasin"));
                mouvement.setQuantite(resultSet.getDouble("quantite"));
                mouvement.setDate_mouvement(resultSet.getDate("date_mouvement"));
                mouvement.setEtat(resultSet.getInt("etat"));
            }
        }catch (Exception e){
            throw e;
        }finally {
            if(close_connection){
                connection.close();
            }
        }
        return mouvement;
    }

    public void insertMouvement(Connection connection, Date date2) throws Exception {
        boolean close_connection = false;
        String sql = null;
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "select valider_mouvement_sortie(?,?,?,?,?,?,?)";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.id);
            System.out.println("eto id="+this.id);
            preparedStatement.setString(2, this.id_article);
            preparedStatement.setString(3, this.id_magasin);
            preparedStatement.setDouble(4, this.quantite);
            System.out.println(this.quantite);
            preparedStatement.setDate(5, this.date_mouvement);
            System.out.println(this.date_mouvement);
            preparedStatement.setDate(6, date2);
            System.out.println(date2);
            preparedStatement.setString(7, this.unite_equivalence);
            preparedStatement.executeQuery();
        }catch (Exception e){
            throw e;
        }
        finally {
            if(close_connection){
                connection.close();
            }
        }
    }

    public void updateDemande(Connection connection) throws Exception {
        boolean close_connection = false;
        String sql = null;
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "update mouvement_entre_demande set id_article = ?, id_magasin = ?, date_mouvement= ?, quantite= ? where id=?";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.id_article);
            preparedStatement.setString(2, this.id_magasin);
            preparedStatement.setDate(3, this.date_mouvement);
            preparedStatement.setDouble(4, this.quantite);
            preparedStatement.setString(5, this.id);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            throw e;
        }
        finally {
            if(close_connection){
                connection.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Mouvement mouvement = new Mouvement();
        ArrayList<Mouvement> mouvements = new ArrayList<>();
        mouvements = mouvement.getDemandes(null);
        for (Mouvement mouvement1: mouvements) {
            System.out.println(mouvement1.getId());
        }
        //mouvement.dateAnterieure(null, "ART1", "MAG1", Date.valueOf("2023-01-01"));
    }
}
