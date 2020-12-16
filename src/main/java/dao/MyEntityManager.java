package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MyEntityManager {
	private EntityManager em;
	private static MyEntityManager instance;
        public EntityManagerFactory emf;
	public MyEntityManager() {
		super();
		// TODO Auto-generated constructor stub
		em = Persistence.createEntityManagerFactory("com.mycompany_mavenproject2_jar_1.0-SNAPSHOTPU").createEntityManager();
                emf = Persistence.createEntityManagerFactory("com.mycompany_mavenproject2_jar_1.0-SNAPSHOTPU");
	}
	 public synchronized  static MyEntityManager getInstance() {
		 if(instance==null)
			 instance = new MyEntityManager();
			 
		 return instance;
	 }
	 public EntityManager getEntityManager() {
		 return em;
	 }
         public  EntityManagerFactory getEmf(){
             return emf;
         }

}
