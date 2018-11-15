# Pay4Park

Starting Fresh
---

The following is the instructions on how to install java and maven on your Ubuntu machine in order to run the applicaion. Open the terminal and follow these instructions


Installing Java
---
Run the following commands in order:

1. Update apt with `sudo apt-get update`
2. Download repository with `sudo add-apt-repository ppa:webupd8team/java`
	- Press `[ENTER]` to confirm install
3. Again, update apt with `sudo apt-get update`
4. Install the Oracle Java Installer with `sudo apt install oracle-java8-installer`
	- Type `Y` to confirm install
	- Two diologs will pop up, say `OK` and `YES` to both respectively
5. Verify installer is the latest verision and set it to default with `sudo apt install oracle-java8-set-default`
6. Java is now installed. Verifiy with `java -version`


Installing Maven
---

1. Change Directory to `/opt` with `cd /opt`
2. Download Maven with the following `sudo wget http://apache.spinellicreations.com/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.tar.gz`
3. You can verify this was downloaded with `ls` 
4. Extract the downloaded file with `sudo tar -xf apache-maven-3.6.0-bin.tar.gz `
5. Rename the extracted folder to something more convienient. We will use `apache-maven` as the new folder name. This is done with the following command `sudo mv apache-maven-3.6.0 apache-maven`
6. Let the terminal know to use maven commands with `sudo update-alternatives --install /usr/bin/mvn maven /opt/apache-maven/bin/mvn 1001` 


Updating Environment Variables 
---
1. Change your director to the following: `cd /etc/profile.d/`
2. Edit `maven.sh` with your editor of choice. To edit without installing any extras you can use the following: `sudo gedit maven.sh`
3. `maven.sh` needs to look exactly as follows:

```
export JAVA_HOME=/usr/lib/jvm/java-8-oracle
export M2_HOME=/opt/apache-maven
export MAVEN_HOME=/opt/apache-maven
export PATH=${M2_HOME}/bin:${PATH}
```

4. Save and return to the terminal
5. Allow the new file to be an executable with `sudo chmod +x maven.sh`
6. Update sources with `source maven.sh`
7. Maven is now installed and configured. Run `mvn --version` to confirm.


Downloading the Application
---
If the application is already downloaded to your system then simply navigate the the directory and skip thsese steps

1. Change your directory to where you want to install the application
2. Run the following to install git `sudo apt install git`
3. Clone the project `git clone https://github.com/bjbloemker/Pay4Park`
4. Navigate to the project folder `cd Pay4Park/`



##The application is now installed. You can now preform several actions:


How to start the Pay4Park application
---

1. Run `mvn clean install` to build the application
2. Start application with `java -jar target/pay4park-1.0-SNAPSHOT.jar server config.yml`
3. You can verify the application is running  by entering the url `http://localhost:8080`


How to run the Unit Test and See Unit Coverage
---
1. Run `mvn clean test` to preform the Unit Tests
2. For full coverage data, run the command from step (1) and then continue on to step 3
3. Navigate to correct directory with the following `cd target/site/jacoco/`
4. The results are in the `index.html` file. It might be best to open them with a web browser. This is acheived by `xdg-open ./index.html`
5. All unit test coverage will appear on that page.
