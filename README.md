# Proiect - Etapa 1 - POO TV - Descrierea Temei


Ne dorim implementarea unei platfome pentru vizualizarea de filme și seriale. Inițial ne gândim la câteva funcționalități standard, primele care ne vin în minte, pe care le vom propune noi după cum urmează: register, login, logout, search, view movie, rating, etc.

Rolul vostru este să implementați funcționalitățile acestei pagini din perspectiva unui utilizator și să îi transmiteți acestuia ce anume are voie și nu are voie să realizeze la un anumit moment de timp, pe platforma voastră.

# Descrierea modului de funcționare al platformei


## Structura

Platforma va funcționa pe principiul unui sistem de fișiere după cum urmează:

Fiecare test de input al platformei va începe de pe un așa numit „Homepage neautentificat”. Mai exact, vorbim despre o pagină standard, o pagină pentru un utilizator ce vrea să acceseze platforma însă momentan nu s-a înregistrat cu niciun cont pe platformă sau are contul său înregistrat, însă nu s-a autentificat cu acesta;
În funcție de pagina pe care se află la un anumit moment dat un utilizator, acesta poate săvârși anumite acțiuni specifice;
Sistemul este unul secvențial, adică la un anumit moment de timp, de pe o anumită pagină, se pot realiza doar două tipuri de operațiuni în această etapă, după cum urmează: se poate naviga pe pagina „următoare” (doar acele pagini care pot fi accesate de pe pagina curentă) sau se pot realiza operațiunile permise pe pagina curentă (numărul și specificul acestor operațiuni diferă de la pagină la pagină și vor fi detaliate pentru fiecare pagină în cele ce urmează);
Orice operațiune care nu face parte din cele două descrise mai sus va fi semnalată printr-o eroare la output. Un exemplu de o astfel de eroare ar fi: Nu se poate sări peste o anumită pagină (chiar dacă noi știm unde trebuie să navigăm, pentru a ajunge la destinație, trebuie luat totul pagină cu pagină, nu putem merge direct la destinație);

## Vizualizarea și explicarea structurii

Aceasta este structura platformei pe care trebuie să o implementați:
![68w8gqg](https://user-images.githubusercontent.com/72265977/226671143-a4df93f3-93ba-44da-ac79-6de927faa603.png)



## Workflow-ul unui user pe platformă

Sunt un utilizator al acestei platforme / aplicații. Primesc ca primă pagină „Homepage neautentificat”;

De aici pot să navighez pe pagina „Register”;
De aici pot să apelez la funcționalitatea de register a acestei pagini;
De aici pot să navighez pe pagina „Login”;
De aici pot să apelez la funcționalitatea de login a acestei pagini;
După ce realizez cu succes una dintre aceste două funcționalități voi fi mutat pe pagina „Homepage autentificat” de unde am acces la trei noi pagini:

De aici pot să navighez în pagina de „Logout” care mă va scoate din utilizatorul cu care sunt autentificat în acest moment și mă va plasa în pagina „Homepage neautentificat”;

De aici pot să navighez în pagina de „Movies” unde îmi va apărea o listă cu toate filmele de pe platformă ce nu sunt interzise în țara mea (explicăm la formatul de input cum ne dăm seama de acest lucru);

De aici pot să navighez către „Homepage autentificat”;

De aici pot să navighez către „Logout” (care a fost explicat deja);

De aici pot să navighez către pagina de „See Details” a filmului;

De aici pot să accesez și să mă întorc în pagina de „Homepage autentificat”;

De aici pot să accesez și să mă întorc în pagina de „Movies”;

De aici pot să accesez pagina de „Upgrades”;

De aici pot să cumpăr filmul prin acțiunea „Purchase”;

De aici pot să vizionez filmul prin „Watch” doar dacă l-am cumpărat mai întâi;

De aici pot să apreciez filmul prin „Like” doar dacă am vizionat filmul în prealabil;

De aici pot să ofer o notă de la 1 la 5 filmului prin „Rate the movie” doar dacă am vizionat filmul în prealabil;

De aici pot să apelez la funcționalitățile „Search” sau ”Filter”. Acestea îmi vor filtra lista de filme disponibile în funcție de diferite criterii care sunt detalitate ulterior.

De aici pot să navighez în pagina de „Upgrades”.

De aici pot să navighez și să mă întorc către „Homepage autentificat”;

De aici pot să navighez către „Movies”;

De aici pot să navighez către „Logout” (care a fost explicat deja);

De aici pot să cumpăr moneda platformei prin acțiunea „Buy tokens”, monedă ce mă va ajuta ulterior să cumpăr filme pe platformă;

De aici pot să cumpăr cont premium prin acțiunea „Buy premium account” ce va avea anumite beneficii atât în această etapă cât și în cea de-a doua.

