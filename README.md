# teszt-kitolto

## Alkalmazás futtatása:

	* **Wildfly:**
	
		1.	Wildfly - 10.0.0.Final - Java EE7 Full & Web Distribution letöltése: http://wildfly.org/downloads/
		
		2.	wildfly kicsomagolása a teszt-kitolto\wildfly\ mappába (elvárt struktúra: teszt-kitolto/wildfly/wildfly-10.0.0.Final/...)
		
		3.	wildfly_config.sh futtatása
	
	* **MySQL Szerver:**
	
		1. MySQL Community Server 5.7.12 letöltése: https://dev.mysql.com/downloads/mysql/
		
		2. MySQL szerver telepítése
		
		3. A root jelszava legyen: "admin"
		
	* **NetBeans 8.1 Services:**
	
		1.	Databases -> Register MySQL Server... 
									Host: localhost
									Port: 3306
									User: root
									Password: admin
									
		2.	A létrejött szerverhez csatlakozás adjunk hozzá egy adatbázist (Create Database)
			Database name: tests	
		
		3.	Servers -> Add : Wildfly 
			Server location:		teszt-kitolto\wildfly\wildfly-10.0.0.Final\
			Server configuration:	teszt-kitolto\wildfly\wildfly-10.0.0.Final\standalone\configuration\standalone-full.xml
			
			

#### A létrejött adatbázishoz való csatlakozás, illetve a wildfly indítása után az alkalmazás futtatható.