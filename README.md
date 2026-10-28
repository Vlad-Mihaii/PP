# **Documentație: Client MQTT v5** {#documentație:-client-mqtt-v5}

**Studenți:** Enache Brianna, Vacaru Vlad-Mihai

## **Cuprins**

* [Documentație: Client MQTT v5](#documentație:-client-mqtt-v5)  
* [1\. Introducere](#1.-introducere)  
  * [1.1 Descrierea protocolului](#1.1-descrierea-protocolului)  
  * [1.2 Prezentare generală a aplicației și cerințe funcționale](#1.2-prezentare-generală-a-aplicației-și-cerințe-funcționale)  
* [2\. Specificații tehnice](#2.-specificații-tehnice)  
  * [2.1 Formatul mesajelor MQTT v5](#2.1-formatul-mesajelor-mqtt-v5)  
  * [2.2 Metodologia de Implementare a Comunicației prin socket](#2.2-metodologia-de-implementare-a-comunicației-prin-socket)  
  * [2.3 Pachetele implementate](#2.3-pachetele-implementate)  
* [3\. Interfața Grafică, Conectare, Autentificare și LastWill](#3.-interfața-grafică,-conectare,-autentificare-și-lastwill)  
  * [3.1 Organizarea Interfeței Grafice (GUI)](#3.1-organizarea-interfeței-grafice-\(gui\))  
  * [3.2 Fluxul de Conectare și Autentificare](#3.2-fluxul-de-conectare-și-autentificare)  
  * [3.3 Gestionarea mecanismului LastWill](#3.3-gestionarea-mecanismului-lastwill)  
* [4\. Implementarea Aplicației Demonstrative (Monitorizare și Afișare)](#4.-implementarea-aplicației-demonstrative-\(monitorizare-și-afișare\))  
  * [4.1 Fluxul de Publicare Periodică (Rolul de Publisher)](#4.1-fluxul-de-publicare-periodică-\(rolul-de-publisher\))  
  * [4.2 Fluxul de Abonare și Afișare (Rolul de Subscriber)](#4.2-fluxul-de-abonare-și-afișare-\(rolul-de-subscriber\))  
* [5\. Metodologia de Implementare a Mecanismului Keep Alive](#5.-metodologia-de-implementare-a-mecanismului-keep-alive)  
* [6\. Metodologia de Implementare pentru Mecanismele QoS 0, 1 și 2](#6.-metodologia-de-implementare-pentru-mecanismele-qos-0,-1-și-2)  
  * [6.1 Fluxul pentru QoS 0 (At most once)](#6.1-fluxul-pentru-qos-0-\(at-most-once\))  
  * [6.2 Fluxul pentru QoS 1 (At least once)](#6.2-fluxul-pentru-qos-1-\(at-least-once\))  
  * [6.3 Fluxul pentru QoS 2 (Exactly once \- Automat în 4 pași)](#6.3-fluxul-pentru-qos-2-\(exactly-once---automat-în-4-pași\))  
* [7\. Modelul de Stări ale Aplicației](#7.-modelul-de-stări-ale-aplicației)  
* [8\. Centralizatorul de Temporizări (Timers)](#8.-centralizatorul-de-temporizări-\(timers\))  
* [9\. Planificarea activităților și repartizarea responsabilităților](#9.-planificarea-activităților-și-repartizarea-responsabilităților)

## **1\. Introducere** {#1.-introducere}

### **1.1 Descrierea protocolului** {#1.1-descrierea-protocolului}

MQTT (Message Queuing Telemetry Transport) versiunea 5.0 este un protocol de mesagerie de nivel aplicație, bazat pe modelul publish/subscribe, care rulează peste stiva TCP/IP. Este conceput să fie extrem de ușor (lightweight) și eficient, fiind ideal pentru conexiuni unde lățimea de bandă este limitată. Spre deosebire de protocoalele bazate pe cerere/răspuns (precum HTTP sau CoAP), MQTT utilizează un Broker central care distribuie mesajele de la publicatori (publishers) către abonați (subscribers) pe baza unor Topic-uri.

Protocolul asigură livrarea mesajelor prin trei niveluri de Calitate a Serviciului (QoS \- Quality of Service) și include mecanisme native pentru menținerea sesiunii (Keep Alive) și gestionarea deconectărilor neprevăzute (Last Will).

### **1.2 Prezentare generală a aplicației și cerințe funcționale** {#1.2-prezentare-generală-a-aplicației-și-cerințe-funcționale}

Proiectul vizează dezvoltarea unei aplicații client MQTT v5 cu interfață grafică, destinată monitorizării parametrilor sistemului de calcul (încărcare CPU, memorie RAM, temperatură) și afișării simultane a datelor de la alte instanțe.

Clientul va citi periodic acești parametri, îi va publica pe topic-uri dedicate și va putea, simultan, să se aboneze pentru a afișa datele primite de la alte instanțe ale aplicației.

Pentru a realiza acest lucru fără biblioteci externe de MQTT, aplicația este proiectată modular, pe trei niveluri principale:

* **Nivelul de Interfață și Control (GUI Layer):** Preia configurațiile de la utilizator (adresă, ID, LastWill, credențiale) și afișează în timp real parametrii primiți din rețea.  
* **Nivelul de Protocol și Sesiune (MQTT v5 Core Layer):** Nucleul aplicației, care transformă comenzile din interfață în pachete binare conform standardului și gestionează starea conexiunii (autentificare, cozi QoS, Keep Alive).  
* **Nivelul de Rețea și Sistem (Network & OS Layer):** Gestionează conexiunea efectivă prin modulul socket și colectează periodic parametrii hardware de la sistemul de operare.

## **2\. Specificații tehnice** {#2.-specificații-tehnice}

### **2.1 Formatul mesajelor MQTT v5** {#2.1-formatul-mesajelor-mqtt-v5}

Orice mesaj trimis pe rețea respectă o structură strictă pe octeți, formată din **Fixed Header** (obligatoriu), urmat, în funcție de tipul pachetului, de **Variable Header** și **Payload**.

**Structura Fixed Header:**

```
  Bit      7   6   5   4   3   2   1   0
        +-------------------------------+
Octet 1 | MQTT Control Packet Type  |Flags|
        +-------------------------------+
Octet 2 | Remaining Length (1-4 octeți) |
        +-------------------------------+
```

**Detalierea câmpurilor din formatul mesajelor:**

| Câmp | Dimensiune | Descriere |
| :---- | :---- | :---- |
| **MQTT Control Packet Type** | 4 biți (biții 7-4, octetul 1\) | Specifică tipul pachetului de control conform standardului (ex: 1 pentru CONNECT, 3 pentru PUBLISH, 12 pentru PINGREQ). |
| **Flags (Indicatori)** | 4 biți (biții 3-0, octetul 1\) | Biți de control specifici fiecărui tip de pachet. Excepția principală este pachetul PUBLISH, unde acești biți conțin: flag-ul DUP (duplicat/retransmisie), nivelul QoS (2 biți) și flag-ul RETAIN. |
| **Remaining Length** | 1 până la 4 octeți | Indică numărul total de octeți rămași în pachet (dimensiunea Variable Header \+ Payload). Folosește codificarea Variable Byte Integer: bitul cel mai semnificativ (MSB) din fiecare octet acționează ca un indicator de continuare, permițând pachete de până la 256 MB. |
| **Variable Header** | Variabilă (opțional) | Prezent în majoritatea pachetelor. Conține elemente de control suplimentare, cum ar fi Packet Identifier (folosit obligatoriu pentru mecanismele QoS 1 și 2\) și secțiunea de Properties (introdusă în MQTT v5 pentru a transmite metadate, Reason Codes sau temporizări). |
| **Payload** | Variabilă (opțional) | Datele efective transportate de pachet. În cazul pachetului CONNECT, conține configurațiile clientului (Client ID, User, Parolă, LastWill). În cazul pachetului PUBLISH, reprezintă datele aplicației demonstrative (valorile monitorizate pentru CPU, memorie, temperatură). |

### 

### **2.2 Metodologia de Implementare a Comunicației prin socket** {#2.2-metodologia-de-implementare-a-comunicației-prin-socket}

Deoarece nu folosim biblioteci dedicate pentru MQTT, toată comunicația se face direct peste o conexiune TCP/IP (folosind modulul standard socket, pe portul 1883). Protocolul MQTT v5 este un protocol binar, ceea ce înseamnă că noi trebuie să construim manual fiecare pachet la nivel de octeți (bytes) și biți înainte să îl trimitem pe socket, și apoi să despachetăm octeții primiți de la Broker.

1. **Metoda de construire a pachetelor (Împachetarea):** Pentru fiecare acțiune MQTT, aplicația folosește o metodă generală care construiește un buffer de octeți în trei pași:  
   * Construiește mai întâi Antetul Variabil și Payload-ul într-un buffer temporar, aplicând metode ajutătoare pentru scrierea numerelor pe 2 octeți și prefixarea textelor cu lungimea lor.  
   * Calculează dimensiunea acestui buffer temporar și o comprimă folosind algoritmul *Variable Byte Integer* (împărțiri succesive la 128\) pentru a obține câmpul Remaining Length.  
   * Pune în față octetul de control din Antetul Fix (tipul pachetului \+ flag-urile), lipește Remaining Length și adaugă buffer-ul temporar, trimițând rezultatul final pe socket.  
2. **Metoda de citire de pe socket (Despachetarea):** Deoarece socket-ul TCP livrează un flux continuu de octeți, recepția nu se face citind o dimensiune fixă la întâmplare. Metoda de recepție funcționează într-o buclă continuă care citește mai întâi primul octet (pentru a afla tipul pachetului), apoi citește octeții de lungime (decodificând Variable Byte Integer), și abia apoi apelează citirea din socket pentru numărul exact de octeți rămași. Astfel, pachetele sunt separate corect și trimise către un Dispecer de Pachete care decide ce trebuie făcut cu fiecare.

### **2.3 Pachetele implementate** {#2.3-pachetele-implementate}

Aplicația implementează un subset precis de pachete de control MQTT v5, limitat strict la necesitățile funcționalității client-broker stabilite în cerințe. În tabelul de mai jos este detaliat fiecare pachet gestionat și interpretat la nivel de socket, direcția sa pe rețea și rolul specific în cadrul aplicației:

| Pachet MQTT | Direcție pe Socket | Rolul și Detaliile Implementării în Aplicație |
| :---- | :---- | :---- |
| **CONNECT** | Client \-\> Broker | Inițiază sesiunea TCP. Payload-ul este construit folosind datele din interfața grafică: Client ID, credențialele de autentificare (utilizator/parolă) și configurarea mesajului LastWill. |
| **CONNACK** | Broker \-\> Client | Confirmă starea conectării prin evaluarea câmpului Reason Code (ex. 0x00 pentru succes, 0x04 sau 0x05 pentru erori de autentificare). |
| **PUBLISH** | Ambele | Transportă datele utile. Este folosit de fluxul Publisher pentru trimiterea periodică a parametrilor sistemului de calcul (CPU, RAM, temperaturi) și de fluxul Subscriber pentru afișarea datelor primite de la alte instanțe. |
| **PUBACK** | Ambele | Confirmă primirea unui mesaj PUBLISH. Reprezintă mecanismul de bază pentru garantarea livrării de nivel QoS 1 (At least once). |
| **PUBREC**  **PUBREL**  **PUBCOMP** | Ambele | Set de trei pachete de control utilizate secvențial (handshake în 4 pași alături de PUBLISH) pentru a asigura mecanismele de livrare QoS 2 (Exactly once). |
| **SUBSCRIBE**  **SUBACK** | Client \-\> Broker Broker \-\> Client | Cererea clientului de a se abona la topic-urile generale din rețea pentru a monitoriza celelalte instanțe, urmată de confirmarea abonării de către Broker. |
| **PINGREQ**  **PINGRESP** | Client \-\> Broker Broker \-\> Client | Constituie mecanismul de Keep Alive. Pachetul de cerere este trimis periodic de client în lipsa altui trafic de date, menținând socket-ul deschis. |
| **DISCONNECT** | Client \-\> Broker | Pachet trimis automat la oprirea normală a aplicației din interfața grafică, instruind Brokerul să închidă conexiunea curat (fără a declanșa publicarea mesajului LastWill). |

## 

## **3\. Interfața Grafică, Conectare, Autentificare și LastWill** {#3.-interfața-grafică,-conectare,-autentificare-și-lastwill}

### **3.1 Organizarea Interfeței Grafice (GUI)** {#3.1-organizarea-interfeței-grafice-(gui)}

Fereastra aplicației este împărțită logic în două zone principale, necesare pentru demonstrarea funcționalităților:

1. **Panoul de Configurare și Conectare:** Conține câmpuri de text pentru adresa IP și portul brokerului, ID-ul clientului, utilizator și parolă. De asemenea, include o secțiune dedicată pentru configurarea mesajului LastWill (topic, conținut mesaj, nivel QoS, retain flag) și un selector pentru nivelul general QoS.  
2. **Panoul de Monitorizare (Dashboard):** O zonă vizuală (tabel sau panouri dinamice) unde sunt afișați în timp real parametrii proprii ai sistemului, alături de datele recepționate de la celelalte instanțe conectate.

### **3.2 Fluxul de Conectare și Autentificare** {#3.2-fluxul-de-conectare-și-autentificare}

1. **Validarea și inițierea:** Aplicația preia datele din câmpurile interfeței și le validează (asigurându-se că adresa brokerului și ID-ul clientului nu sunt goale), apoi deschide conexiunea TCP prin socket către broker.  
2. **Construirea pachetului:** Se apelează metoda de construire a pachetului CONNECT. Pe baza câmpurilor completate în GUI, se compune octetul de opțiuni (Connect Flags) folosind operații pe biți:  
   * Dacă utilizatorul a introdus Username și Parolă, se activează biții de autentificare.  
   * Dacă secțiunea LastWill este completată, se activează biții asociați pentru Will.  
3. **Trimiterea și Răspunsul:** Informațiile (ID client, topic/mesaj LastWill, utilizator, parolă) sunt adăugate în Payload în ordinea strictă cerută de protocol și trimise pe socket. La primirea CONNACK, dacă Reason Code este 0x00, aplicația trece în starea „Conectat” și pornește firele de execuție pentru monitorizare și Keep Alive. O eroare de autentificare determină închiderea socket-ului și afișarea unui mesaj de eroare pe ecran.

### **3.3 Gestionarea mecanismului LastWill** {#3.3-gestionarea-mecanismului-lastwill}

Din punct de vedere al aplicației client, mesajul LastWill nu necesită un fir de execuție separat pentru publicare, responsabilitatea revenirii Brokerului. Implementarea noastră acoperă:

* **Împachetarea:** Transmiterea configurației LastWill la inițierea conexiunii (în pachetul CONNECT).  
* **Deconectarea normală:** Când utilizatorul oprește intenționat aplicația, se trimite mai întâi pachetul DISCONNECT pe socket și abia apoi se închide conexiunea TCP. Astfel, Brokerul știe că nu a fost o avarie și nu publică mesajul.  
* **Căderea neprevăzută:** Dacă aplicația se închide forțat sau pică rețeaua, pachetul DISCONNECT nu este trimis. Absența acestuia declanșează publicarea automată a mesajului LastWill de către Broker către restul rețelei.

## **4\. Implementarea Aplicației Demonstrative (Monitorizare și Afișare)** {#4.-implementarea-aplicației-demonstrative-(monitorizare-și-afișare)}

Pentru ca aplicația să funcționeze simultan atât ca Publisher (trimițând proprii parametri), cât și ca Subscriber (citind parametrii altora), logica se bazează pe două fluxuri de lucru paralele care pornesc imediat după confirmarea conectării:

### **4.1 Fluxul de Publicare Periodică (Rolul de Publisher)** {#4.1-fluxul-de-publicare-periodică-(rolul-de-publisher)}

* Se creează un Colector de Telemetrie care rulează pe baza unui timer (ex. la fiecare 3-5 secunde).  
* La declanșare, interoghează sistemul de operare pentru a citi valorile curente (procent CPU, memorie RAM utilizată, temperatură).  
* Pentru fiecare parametru, formează dinamic numele topic-ului folosind ID-ul clientului (ex: sistem/\<ID\_Client\>/cpu) și apelează metoda de publicare. Aceasta împachetează valoarea într-un pachet PUBLISH și îl trimite către Broker respectând QoS-ul setat.


### **4.2 Fluxul de Abonare și Afișare (Rolul de Subscriber)** {#4.2-fluxul-de-abonare-și-afișare-(rolul-de-subscriber)}

* Imediat după conectare, aplicația trimite automat un pachet SUBSCRIBE către un topic cu wildcard (ex: sistem/\#), abonându-se la toate mesajele de monitorizare din rețea.  
* Când Firul de Recepție primește un PUBLISH de pe socket, extrage Topic-ul și Payload-ul (valoarea parametrului).  
* Se execută o operație de despărțire (split) a topic-ului după caracterul / pentru a izola sursa mesajului (ID\_Client) și tipul parametrului (CPU, memorie, temp).  
* Datele sunt trimise către GUI: dacă acel ID\_Client este nou, interfața creează automat o secțiune/rând nou în Dashboard. Dacă există deja, valorile sunt actualizate în timp real.

## **5\. Metodologia de Implementare a Mecanismului Keep Alive** {#5.-metodologia-de-implementare-a-mecanismului-keep-alive}

Pentru a detecta căderile de rețea, mecanismul Keep Alive este implementat printr-un Monitor de Activitate ce rulează în fundal pe durata conexiunii:

1. **Monitorizarea traficului:** Se memorează momentul exact (timestamp) al ultimului pachet trimis pe socket. Orice trimitere de date (PUBLISH, SUBSCRIBE etc.) resetează acest cronometru la timpul curent.  
2. **Verificarea periodică:** Un fir de execuție verifică la fiecare secundă timpul scurs de la ultimul pachet trimis.  
3. **Trimiterea PINGREQ:** Dacă timpul se apropie de valoarea Keep Alive stabilită, firul construiește și trimite automat pachetul PINGREQ pe socket, marcând o stare de așteptare.  
4. **Confirmarea PINGRESP:** Când Firul de Recepție primește pachetul PINGRESP de la Broker, starea de așteptare este ștearsă, confirmând stabilitatea rețelei. Dacă trece un timp de timeout fără a sosi acest răspuns, Monitorul declară conexiunea căzută, închide socket-ul și notifică utilizatorul în GUI.  
   

## **6\. Metodologia de Implementare pentru Mecanismele QoS 0, 1 și 2** {#6.-metodologia-de-implementare-pentru-mecanismele-qos-0,-1-și-2}

Pentru a garanta nivelurile de livrare, aplicația utilizează un Manager de Sesiune și Tranzacții QoS. Acesta integrează un Packet ID Generator (secvențial între 1 și 65.535) și structuri de date (dicționare/liste) pentru evidența pachetelor aflate în tranzit:

### **6.1 Fluxul pentru QoS 0 (At most once)** {#6.1-fluxul-pentru-qos-0-(at-most-once)}

* **La trimitere:** Pachetul PUBLISH este construit fără Packet ID, este scris direct pe socket și tranzacția se încheie imediat (nu se reține în memorie).  
* **La primire:** Datele sunt extrase și trimise direct către GUI. Nu se trimite niciun răspuns de confirmare.


### **6.2 Fluxul pentru QoS 1 (At least once)** {#6.2-fluxul-pentru-qos-1-(at-least-once)}

* **La trimitere:** Se generează un Packet ID. Pachetul PUBLISH este salvat într-un dicționar de tranzacții („În așteptare PUBACK”) și trimis pe socket. La primirea unui PUBACK valid de la Broker, mesajul este șters din dicționar. Dacă mesajul rămâne blocat un anumit timp fără confirmare, un mecanism de retry îl retrimite pe socket activând flag-ul de duplicat (DUP \= 1).  
* **La primire:** După afișarea datelor din PUBLISH, aplicația extrage ID-ul și trimite imediat înapoi pe socket un pachet PUBACK cu același ID.


### **6.3 Fluxul pentru QoS 2 (Exactly once \- Automat în 4 pași)** {#6.3-fluxul-pentru-qos-2-(exactly-once---automat-în-4-pași)}

* **La trimitere:**  
  1. Se alocă un Packet ID, se salvează mesajul cu starea AȘTEPTARE\_PUBREC și se trimite PUBLISH.  
  2. La primirea pachetului PUBREC, starea tranzacției se schimbă în AȘTEPTARE\_PUBCOMP, și aplicația trimite automat pachetul PUBREL.  
  3. La recepționarea pachetului final PUBCOMP, tranzacția este ștearsă din dicționar.  
* **La primire:**  
  1. La primirea unui PUBLISH cu QoS 2, aplicația verifică o listă internă de „ID-uri primite”. Dacă e un mesaj nou, îl adaugă în listă, trimite parametrii către GUI și răspunde Brokerului cu PUBREC.  
  2. Ulterior, când Brokerul trimite PUBREL, aplicația șterge ID-ul din listă și încheie schimbul trimițând înapoi PUBCOMP.  
     

**Reprezentarea vizuală a fluxului QoS 2:**

```
 Client (Publisher)                             Broker
       |                                          |
       | ------ PUBLISH (QoS 2, Packet ID) -----> |  (Stare: AȘTEPTARE_PUBREC)
       |                                          |
       | <----- PUBREC (Packet ID) -------------- |  
       |                                          |
       | ------ PUBREL (Packet ID) -------------> |  (Stare: AȘTEPTARE_PUBCOMP)
       |                                          |
       | <----- PUBCOMP (Packet ID) ------------- |  (Tranzacție finalizată)
       |                                          |
```

## **7\. Modelul de Stări ale Aplicației** {#7.-modelul-de-stări-ale-aplicației}

Pentru a gestiona corect comunicația și tranzacțiile detaliate anterior, aplicația folosește un automat de stări intern. Acestea dictează etapele conexiunii și stadiul mesajelor aflate în tranzit:

| Categorie | Starea internă | Descriere / Condiție de tranziție |
| :---- | :---- | :---- |
| **Conexiune** | DECONECTAT | Starea inițială sau starea atinsă după o deconectare (normală sau forțată de erori de rețea). |
| **Conexiune** | CONNECTING | S-a deschis socket-ul TCP și s-a trimis pachetul CONNECT. Se așteaptă răspunsul CONNACK. |
| **Conexiune** | CONNECTED | S-a primit CONNACK cu succes. În această stare pornesc modulele de monitorizare și Keep Alive. |
| **Keep Alive** | AȘTEPTARE\_PING | S-a trimis un PINGREQ pe bază de timer și se așteaptă confirmarea prin PINGRESP. |
| **QoS 1** | AȘTEPTARE\_PUBACK | S-a trimis un PUBLISH (QoS 1). Tranzacția stă în dicționar până la primirea PUBACK. |
| **QoS 2** | AȘTEPTARE\_PUBREC | S-a trimis PUBLISH (QoS 2, Pasul 1). Se așteaptă PUBREC de la Broker. |
| **QoS 2** | AȘTEPTARE\_PUBCOMP | S-a primit PUBREC și s-a trimis PUBREL (Pasul 3). Se așteaptă finalizarea prin PUBCOMP. |

## 

## **8\. Centralizatorul de Temporizări (Timers)** {#8.-centralizatorul-de-temporizări-(timers)}

Pentru a funcționa non-blocant și a menține starea rețelei, aplicația se bazează pe trei temporizatoare principale:

| Nume Timer | Scopul în aplicație | Declanșare și Acțiune |
| :---- | :---- | :---- |
| **Keep Alive Timer** | Menținerea conexiunii TCP active în lipsa traficului. | Verifică la fiecare secundă timpul scurs de la ultimul pachet trimis. Dacă atinge pragul setat, trimite PINGREQ. |
| **Publish Timer** | Susținerea aplicației demonstrative (Colectorul de telemetrie). | Declanșează periodic (ex: la fiecare 5 secunde) citirea parametrilor (CPU, RAM) și trimiterea pachetelor PUBLISH. |
| **QoS Retry Timer** | Garantarea livrării pachetelor pentru QoS 1 și 2\. | Dacă un mesaj stă în dicționar în starea AȘTEPTARE\_PUBACK / PUBREC mai mult de un *timeout* definit (ex. 5s), se retransmite cu flag-ul DUP=1. |

## 

## **9\. Planificarea activităților și repartizarea responsabilităților** {#9.-planificarea-activităților-și-repartizarea-responsabilităților}

Proiectarea modulară pe niveluri a permis o divizare clară a sarcinilor în cadrul echipei:

* **Enache Brianna:**  
  * **GUI & Control:** Realizarea interfeței grafice (cele 2 panouri logice), validarea câmpurilor de conectare și setarea fluxului LastWill.  
  * **Aplicația Demonstrativă (Monitorizare):** Implementarea Colectorului de Telemetrie (citire CPU, RAM, temperatură).  
  * **Procesare & Afișare:** Implementarea logicii de procesare a topic-urilor (sistem/\<ID\>/parametru) și crearea dinamică a rândurilor în Dashboard.  
* **Vacaru Vlad-Mihai:**  
  * **Network Layer:** Implementarea conexiunii TCP prin modulul nativ de socket-uri și a funcțiilor de împachetare/despachetare bazate pe algoritmul Variable Byte Integer.  
  * **QoS Core (Manager Sesiune):** Construcția mașinii de stări pentru pachetele de control și a logicii de dicționare pentru retransmisii, asigurând fluxurile QoS 1 și 2\.  
  * **Keep Alive:** Implementarea Monitorului de Activitate și a logicii de PINGREQ/PINGRESP pe bază de cronometru.  
    

