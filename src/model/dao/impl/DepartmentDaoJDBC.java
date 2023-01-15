package model.dao.impl;

import java.security.DrbgParameters.Instantiation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn = null;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department dep) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO department " +
                    "(name) " + 
                    "VALUES " +
                    "(?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, dep.getName());

            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    dep.setId(id);
                }
                else {
                    throw new DbException("Nenhuma linha AFETADA!");
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Department dep) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE department " + 
                    "SET name = ?" +
                    "WHERE id = ?");
            st.setString(1, dep.getName());
            st.setInt(2, dep.getId());

            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Atualizado!");
            } else {
                throw new DbException("Linhas não afetadas");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteByIda(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM department " +
                    "WHERE id = ?");
            st.setInt(1, id);
        
            int linhasAfetadas = st.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Department DELETADO!");
            } else {
                throw new DbException("Nenhuma linha afetada");
            }
            
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Department findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT * FROM department " +
                    "WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department dep = InstantiationDepartment(rs);
                return dep;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        return null;
    }

    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM department");
            rs = st.executeQuery();
            List<Department> listDep = new ArrayList<>();
            while (rs.next()) {
                Department dep = new Department(rs.getInt("id"), rs.getString("name"));

                listDep.add(dep);

            }
            return listDep;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private Department InstantiationDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();

        dep.setId(rs.getInt("id"));
        dep.setName(rs.getString("name"));

        return dep;
    }


}

