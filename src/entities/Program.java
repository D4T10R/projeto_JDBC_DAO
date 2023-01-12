package entities;

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
        

    }

}
