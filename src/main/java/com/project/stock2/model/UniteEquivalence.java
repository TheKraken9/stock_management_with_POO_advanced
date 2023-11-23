package com.project.stock2.model;

import com.project.stock2.util.connecting.Connecting;

import java.util.ArrayList;

public class UniteEquivalence {
    private String id;
    private String id_article;
    private String libelle;
    private double quantite;

    public UniteEquivalence() {
    }

    public UniteEquivalence(String id, String id_article, String libelle, double quantite) {
        this.id = id;
        this.id_article = id_article;
        this.libelle = libelle;
        this.quantite = quantite;
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

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public ArrayList<UniteEquivalence> getUnitesEquivalence() throws Exception{
        ArrayList<UniteEquivalence> unites = new ArrayList<>();
        String sql = "SELECT * FROM unite_quivalence";
        try{
            java.sql.PreparedStatement preparedStatement = Connecting.getConnection("postgres").prepareStatement(sql);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                UniteEquivalence unite = new UniteEquivalence();
                unite.setId(resultSet.getString("id"));
                unite.setId_article(resultSet.getString("id_article"));
                unite.setLibelle(resultSet.getString("libelle"));
                unite.setQuantite(resultSet.getDouble("quantite"));
                unites.add(unite);
            }
        }catch (Exception e){
            throw e;
        }
        return unites;
    }
}
