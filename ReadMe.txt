I tackled this problem by first doing research on what I believed I would be most unfamiliar with, or may cause the most problems. In this case, it was read from an html file, adding information to it, and repeating this process. After I spent some time figuring out how I would approach this, I proceeded to build out the program to the specifications required. Once that was complete, the only thing I needed to do was add in the ability to read and write/modify the html file. I saw that using third party libraries was a possibility and found one called JSoup which makes working with HTML very easy. After reading up on it, I added the library in, added some code, and the program was completed. 



The JSoup library can be found here: https://jsoup.org/



Instructions for Compiling and Running with the Command Line:

1. Please keep all provided files in the same folder/directory.
2. Open Command Line and navigate to the directory with all the files and jsoup Jar

3. Enter "javac -cp jsoup-1.9.2.jar ATM.java BankAccount.java"  <--- COMPILE

4. Enter "java -cp .;jsoup-1.9.2.jar ATM"   <----- RUN
