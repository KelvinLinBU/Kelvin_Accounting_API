package com.example.Kelvin_Accounting_API.model;

import jakarta.persistence.*;
import java.util.List;
import com.example.Kelvin_Accounting_API.model.balancesheetmodels.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * Model class for a balance sheet 
 * in a rudimentary accounting software
 * <p>A balance sheet provides a summary of the financial balances of an entity.
 * It is divided into three main sections:</p>
 * <ul>
 *     <li><b>Assets:</b> Resources owned by the entity.</li>
 *     <li><b>Liabilities:</b> Obligations or debts owed by the entity.</li>
 *     <li><b>Equities:</b> The residual interest in the assets after deducting liabilities.</li>
 * </ul>
 * 
 * <p>This class maps to the "balance_sheets" table in the database, with related
 * assets, liabilities, and equities stored in their respective tables.</p>
 * 
 * @author Kelvin
 * @version 1.0
 * @since 2025-01-02
 */

@Entity
@Table(name = "balance_sheets")
public class BalanceSheet {
    /**
     * This is a unique id for the generated balance sheet
     * <p>This field will serve as the primary key for the 
     * balance_sheets table in the database.</p>
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    /**
     * This is the company that the balance sheet will be associated with.
     * <p>Not a mandatory field. Placeholder of ABC Company
     */
    private String company_name;

    /**
     * This is the date that the balance sheet will be associated with.
     * <p>This is a mandatory field and is in the format: MM-DD-YYYY
     */
    @Column(nullable = false)
    private String date;

    /**
     * This is the list of assets that are included in the balance sheet. 
     * <p>Each asset is mapped using a one-to-many relationship and is stored in "assets" table
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "balanceSheet")
    @JsonManagedReference("asset-balanceSheet")
    private List<Asset> assets; 

    /**
     * This is the list of liabilities to be included in the balance sheet.
     * <p>Each liability is mapped using a one-to-many relationship and is stored in the "liabilities" table. 
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "balanceSheet")
    @JsonManagedReference("liability-balanceSheet")
    private List<Liability> liabilities; 

    /**
     * This is the list of equities to be included in the balance sheet.
     * <p>Each equity is mapped using a one-to-many relationship and is stored in the "equities" table. 
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "balanceSheet")
    @JsonManagedReference("equity-balanceSheet")
    private List<Equity> equities; 

    /**
     * Constructor for creating an empty balance sheet. 
     */
    public BalanceSheet(){}

    /**
     * Construct a new balance sheet with the specified date, assets, liabilities, and equities
     * @param company_name Company name
     * @param date Date of the balance sheet
     * @param assets List of assets
     * @param liabilities List of liabilities
     * @param equities List of equities
     */
    public BalanceSheet(String company_name, String date, List<Asset> assets, List<Liability> liabilities, List<Equity> equities){
      
        System.out.println("here"); 
        company_name = company_name.trim();
        String[] words = company_name.split("\\s+");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                formattedName.append(Character.toUpperCase(word.charAt(0)))  // Uppercase first letter
                             .append(word.substring(1).toLowerCase())         // Lowercase the rest
                             .append(" ");                                    // Add a space
            }
        
        
        this.company_name = formattedName.toString().trim(); 
    }
        if (this.company_name == null || this.company_name.isBlank() || this.company_name.trim().isEmpty() || this.company_name == ""){
            this.company_name = "ABC Corp"; 
        }
        this.date = date;
        this.assets = assets;
        this.liabilities = liabilities; 
        this.equities = equities; 
    }


    /**
     * retrieve unique id for balance sheet
     * @return return the balance sheet id
     */
    public Long getId(){
        return id; 
    }

    /**
     * Set unique id for balance sheet
     * @param id takes in the balance sheet Id
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * Retrieve company of the balance sheet
     * @return return the company name
     */
    public String getCompany_name(){
        return company_name; 
    }

    /**
     * set new company of the balance sheet
     * @param company_name takes in company_name String
     */
    public void setCompany_name(String company_name){
        this.company_name = company_name; 
    }

    /**
     * Retrieve date of the balance sheet
     * @return return the balance sheet date
     */
    public String getDate(){
        return date; 
    }

    /**
     * Set date for balance sheet
     * @param date takes in date String
     */
    public void setDate(String date){
        this.date = date; 
    }

    /**
     * Retrieve list of assets
     * @return return the list of assets
     */
    public List<Asset> getAssets(){
        return assets; 
    }

    /**
     * Set assets for balance sheet
     * @param assets takes in list of assets
     */
    public void setAssets(List<Asset> assets){
        this.assets = assets; 
    }

    /**
     * Retrieve list of liabilities
     * @return return the list of liabilities
     */
    public List<Liability> getLiabilities(){
        return liabilities; 
    }

     /**
     * Set liabilities for balance sheet
     * @param liabilities takes in list of liabilities
     */
    public void setLiabilities(List<Liability> liabilities){
        this.liabilities = liabilities; 
    }

    /**
     * Retrieve list of equities
     * @return return the list of equities
     */
    public List<Equity> getEquities(){
        return equities; 
    }

     /**
     * Set equities for balance sheet
     * @param equities takes in list of equities
     */
    public void setEquities(List<Equity> equities){
        this.equities = equities; 
    }

    /**
     * Returns a string representation of the balance sheet.
     * 
     * @return A string representation of the balance sheet.
     */
    @Override
    public String toString() {
        return "BalanceSheet{" +
               "id=" + id + ", name=" + company_name + 
               ", date='" + date + '\'' +
               ", assets=" + assets +
               ", liabilities=" + liabilities +
               ", equities=" + equities +
               '}';

}
}
