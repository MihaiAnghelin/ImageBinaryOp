# Binary Operation (AND, OR, XOR) between two images
### Mihai Anghelin 331AA - Semigrupa 2

## Introducere
> Aplicație ce realizează operații binare între două imagini.

## Descriere
> Aplicația primește ca parametrii de intrare două imagini, si denumirile imaginilor 
> rezultate. Aplicația realizează operații binare între cele două imagini, și salvează 
> rezultatele în fișierele date ca parametrii de ieșire.

## Partea teoretică
> Pentru a realiza operații binare între două imagini, trebuie să le convertim în
> matrice de pixeli. Pentru a realiza operația AND/OR/XOR, trebuie să facem AND/OR/XOR între
> fiecare pixel din prima imagine cu pixelul corespunzător din a doua imagine.

## Implementare
> Pentru a se citi imaginile se folosește un thread Producer, care citeste imaginile cate 
> 1/4 odata și le pune într-un buffer. Un thread Consumer ia din buffer sferturile de
> imagine și le construieste. Pentru a realiza operația binara, se transforma fiecare
> imagine din tablou de bytes intr-un obiect de tip BufferedImage, apoi se realizeaza
> operatia binara pe fiecare pixel din imaginea rezultata și la final se salveaza imaginea
> la locatia data ca parametru de iesire.

## Descriere structurală
> Aplicația este structurata pe baza unui design pattern Producer-Consumer. Aplicatia
> are 3 thread-uri: un thread Producer, un thread Consumer, si un thread Main. Thread-ul
> Producer citeste imaginile si le pune intr-un buffer. Thread-ul Consumer ia din buffer
> sferturile de imagine si le construieste. Thread-ul Main este responsabil cu citirea
> parametrilor de intrare si de iesire, si de pornirea thread-urilor Producer si Consumer.
> Thread-urile Producer si Consumer sunt sincronizate prin intermediul unui obiect de tip
> Buffer, care este un buffer circular. Metodele din clasa Buffer sunt sincronizate, astfel
> ca thread-urile Producer si Consumer sa nu acceseze aceleasi resurse simultan.
> Clasele Consumer si Producer implementeaza interfata Runnable. Clasa Consumer de asemenea
> moștenește clasa ImageProcessing care la randul ei moștenește clasa BmpImage care
> implementeaza interfata IBmpImage. Clasa ImageProcessing contine metodele de procesare
> a imaginilor, iar clasa BmpImage contine metodele de citire si scriere a imaginilor.
> 
> Pentru a masura timpul de execuție al operațiilor facute, se folosește clasa Timer, care
> are două metode: start() și stop(). Metoda start() este apelata la începutul operației,
> iar metoda stop() este apelata la sfârșitul operației. Diferența dintre cele două
> valori reprezintă timpul de execuție al operației.
> Pentru a măsura timpul de execuție al întregului program, se folosește clasa
> GlobalTimer, care are două metode: start() și stop(). Metoda start() este apelata la
> începutul programului, iar metoda stop() este apelata la sfârșitul programului.
> Diferența dintre cele două valori reprezintă timpul de execuție al întregului program.
> 
> Clasa ConsoleIO are metode pentru citirea parametrilor de intrare si de iesire, si
> pentru afisarea mesajelor in consola.


## Evaluare performanță
> Pentru a evalua performanța programului, am folosit două imagini de dimensiuni diferite
> (640x426px si 512x512px). Am realizat operația AND între cele două imagini, iar
> rezultatul a fost salvat într-un fișier nou. Am măsurat timpul de execuție al
> operațiilor. Operațiia AND a fost realizata în 78 ms, Operațiia OR a fost realizata în
> 14ms, iar Operațiia XOR a fost realizata în 10ms. Timpul de execuție al
> întregului program a fost de 33962 ms (33,962 s), incluzând timpul de citire a
> parametrilor de intrare si de iesire de la tastatura. 