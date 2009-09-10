package org.ndot.spring25.transaction.pojo;



/**
 * Usertab entity. @author MyEclipse Persistence Tools
 */

public class Usertab  implements java.io.Serializable {


    // Fields    

     private String id;
     private String name;
     private String age;
     private String sex;


    // Constructors

    /** default constructor */
    public Usertab() {
    }

	/** minimal constructor */
    public Usertab(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public Usertab(String id, String name, String age, String sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return this.age;
    }
    
    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return this.sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
   








}