package com.project.stock2.model;

import com.project.stock2.util.connecting.Connecting;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

public class Magasin {
    private String id;
    private String libelle;

    public Magasin() {
    }

    public Magasin(String id, String libelle) {
        this.id = id;
        this.libelle = libelle;
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

    public String getNomMagasin() throws Exception {
        String sql = "SELECT libelle FROM magasin WHERE id like ? limit 1";
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

    private Magasin magasinExists(Connection connection) throws Exception{
        boolean close_connection = false;
        String sql = null;
        Magasin magasin = new Magasin();
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "SELECT * FROM magasin WHERE id like ? limit 1";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.id);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                magasin.setId(resultSet.getString("id"));
                magasin.setLibelle(resultSet.getString("libelle"));
            }
        }catch (Exception e){
            throw e;
        }finally {
            if(close_connection){
                connection.close();
            }
        }
        return magasin;
    }

    /*public ArrayList<EtatDeStock> getEtatDeStockParMouvement(Connection connection, String id_article, Date date_etat_de_stock, String id_mouvement) throws Exception{
        boolean close_connection = false;
        String sql = null;
        ArrayList<EtatDeStock> etatDeStocks = new ArrayList<>();
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "SELECT * FROM calculer_reste(?, ?, ?) where id = ?";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, date_etat_de_stock);
            preparedStatement.setString(2, id_article);
            preparedStatement.setString(3, this.id);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                EtatDeStock etatDeStock = new EtatDeStock();
                etatDeStock.setId_mouvement(resultSet.getString("id_mouvement"));
                etatDeStock.setId_article(resultSet.getString("id_article"));
                etatDeStock.setId_magasin(resultSet.getString("id_magasin"));
                etatDeStock.setQuantite(resultSet.getInt("quantite"));
                etatDeStock.setPrix(resultSet.getDouble("prix"));
                etatDeStock.setDate(resultSet.getString("date"));
                etatDeStocks.add(etatDeStock);
            }
        }catch (Exception e){
            throw e;
        }finally {
            if(close_connection){
                connection.close();
            }
        }
        return etatDeStocks;
    }*/

    public ArrayList<Magasin> getMagasins(Connection connection) throws Exception {
        boolean close_connection = false;
        String sql = null;
        ArrayList<Magasin> magasins = new ArrayList<>();
        if(connection == null){
            close_connection = true;
            connection = Connecting.getConnection("postgres");
        }
        sql = "SELECT * FROM magasin";
        try{
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Magasin magasin = new Magasin();
                magasin.setId(resultSet.getString("id"));
                magasin.setLibelle(resultSet.getString("libelle"));
                magasins.add(magasin);
            }
        }catch (Exception e){
            throw e;
        }finally {
            if(close_connection){
                connection.close();
            }
        }
        return magasins;
    }


    public static void main(String[] args) throws Exception {
        Magasin magasin = new Magasin();
        magasin.setId("MAG1");
        //magasin.magasinExists(null, "MAG1");
    }
}
