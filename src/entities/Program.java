package entities;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSelelerDao();

        System.out.println("\n=== TESTE 1: Seller findById ===");
        Seller seller = sellerDao.findById(3);

        System.out.println(seller);

        System.out.println("\n=== TESTE 2: Seller findByIdDepartment ===");
        Department dep = new Department(2, null);
        List<Seller> listSeller = sellerDao.findByDepartment(dep);
        for (Seller sel : listSeller) {
            System.out.println(sel);
        }

        System.out.println("\n=== TESTE 3: Seller findAll ===");
        List<Seller> list = sellerDao.findAll();
        for(Seller sel : list) {
            System.out.println(sel);
        }
        
        System.out.println("\n=== TESTE 4: Seller insert ===");
        Seller newSeller = new Seller(null, "Leonardo", "Leonardoweder@gamail.com", new Date(), 3.500, dep);
        sellerDao.insert(newSeller);
        System.out.println("Id do seller inserido: " + newSeller.getId());

    }

}
