package dao;

import java.util.List;
import java.util.Scanner;
import metier.Produit;

public class TestDao {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProduitDaoImpl pdao = new ProduitDaoImpl();

        Produit prod = pdao.save(new Produit("iphone 8 plus", 2800));
        System.out.println(prod);

        List<Produit> prods = pdao.searchByNom("HP");
        for (Produit p : prods) {
            System.out.println(p);
        }

        Long productId = 1L;
        Produit fetchedProd = pdao.getProduit(productId);
        System.out.println("Fetched Product by ID: " + fetchedProd);

        System.out.print("Entrez l'ID du produit que vous souhaitez mettre à jour : ");
        Long updateProductId = scanner.nextLong();

        System.out.print("Entrez le nouveau prix du produit : ");
        Double newPrice = scanner.nextDouble();

        Produit productToUpdate = pdao.getProduit(updateProductId);
        if (productToUpdate != null) {
            productToUpdate.setPrix(newPrice);
            pdao.updateProduit(productToUpdate);
            System.out.println("Produit mis à jour : " + productToUpdate);
        } else {
            System.out.println("Aucun produit trouvé pour la mise à jour avec l'ID " + updateProductId);
        }

        System.out.print("Entrez l'ID du produit que vous souhaitez supprimer : ");
        Long deleteProductId = scanner.nextLong();

        pdao.deleteProduit(deleteProductId);
        System.out.println("Produit supprimé avec l'ID : " + deleteProductId);

        List<Produit> prods1 = pdao.searchByNom("HP");
        for (Produit p : prods1) {
            System.out.println(p);
        }

        scanner.close();
    }
}
