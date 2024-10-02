import Database.MyConnection;

import java.sql.*;
import java.util.HashMap;

public class InsertAllElementsIntoSenyawaTable {
    static String[] elements = {"H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na", "Mg", "Al", "Si", "P", "S", "Cl", "K", "Ar",
            "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Ni", "Co", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr",
            "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb", "I", "Te", "Xe", "Cs", "Ba", "La", "Ce", "Pr", "Nd", "Pm",
            "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb", "Lu", "Hf", "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb",
            "Bi", "Th", "Pa", "U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md", "No", "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt",
            "Ds", "Rg", "Cn", "Nh", "Fl", "Mc", "Lv", "Ts", "Og"};
    static double[] arValues = {1.008, 4.0026, 6.94, 9.0122, 10.81, 12.011, 14.007, 15.999, 18.998, 20.180, 22.990, 24.305, 26.982, 28.085, 30.974, 32.06, 35.453, 39.098, 39.948,
            40.078, 44.956, 47.867, 50.942, 51.996, 54.938, 55.845, 58.693, 58.933, 63.546, 65.38, 69.723, 72.630, 74.922, 78.971, 79.904, 83.798, 85.468, 87.62, 88.906, 91.224,
            92.906, 95.95, 98.0, 101.07, 102.91, 106.42, 107.87, 112.41, 114.82, 118.71, 121.76, 126.90, 127.60, 131.29, 132.91, 137.33, 138.91, 140.12, 140.91, 144.24, 145.00, 150.36,
            151.96, 157.25, 158.93, 162.50, 164.93, 167.26, 168.93, 173.05, 174.97, 178.49, 180.95, 183.84, 186.21, 190.23, 192.22, 195.08, 196.97, 200.59, 204.38, 207.2, 208.98, 232.04,
            231.04, 238.03, 237.00, 244.00, 243.00, 247.00, 247.00, 251.00, 252.00, 257.00, 258.00, 259.00, 262.00, 267.00, 270.00, 271.00, 270.00, 277.00, 276.00, 281.00, 280.00, 285.00,
            284.00, 289.00, 288.00, 293.00, 294.00, 294.00};


    public static void main(String[] args) {
        String QUERY = "INSERT INTO senyawa (label, value) VALUES (?, ?)";
        try (Connection conn = MyConnection.connnect()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY)) {
                for (int i = 0; i < elements.length; i++) {
                    ps.setString(1, elements[i]);
                    ps.setDouble(2, arValues[i]);
                    ps.executeUpdate();
                    System.out.println("Data ditambahkan !!!");
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
