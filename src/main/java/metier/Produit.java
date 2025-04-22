package metier;

import java.io.Serializable;

public class Produit implements Serializable {
    private Long idProduit;  // Renommé pour être plus clair
    private String nomProduit;  // Renommé pour être plus cohérent
    private double prix;

    // Constructeur sans arguments
    public Produit() {
        super();
    }

    // Constructeur avec nom et prix
    public Produit(String nomProduit, double prix) {
        super();
        this.nomProduit = nomProduit;  // Utilisation du nom cohérent
        this.prix = prix;
    }

    // Getter et setter pour idProduit
    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    // Getter et setter pour nomProduit
    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    // Getter et setter pour prix
    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    // Optionnel : redéfinir la méthode toString pour un affichage plus facile des objets Produit
    @Override
    public String toString() {
        return "Produit [idProduit=" + idProduit + ", nomProduit=" + nomProduit + ", prix=" + prix + "]";
    }
}
