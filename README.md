# Tema 2 Programare Orientata pe Obiecte 2018
Implementarea unui VCS.

Pentru implementarea temei am pastrat scheletul de cod dat si am adaugat in
pachetul vcs clasele: Branch, BranchOperation, Commit, CommitOperation,
CheckoutOperation, LogOperation, RollbackOperation, StatusOperation,
InvalidVcsOperation.

In clasa Vcs, am adaugat membrul staging, o lista de stringuri, in care am
retinut (cu ajutorul metodei track) actiunile care au alterat sistemul de
fisiere. Aceasta lista este golita la fiecare comanda commit si rollback.

Am implementat conceptul de branch in clasa Branch care contine o lista de
commituri, un sistem de fisiere si un nume. Pentru conceptul de commit am 
creat clasa Commit care contine un mesaj, un id si un sistem de fisiere.
Id-ul fiecarui commit l-am generat folosit IDGenerator.

In clasa CommitOperation am creat un nou commit, l-am adaugat in lista de
commituri a branchului curent, am setat headul pe el si am actualizat sistemul
se fisiere al branchului pe care se afla. Daca nu exista comenzi in staging,
se returneaza eroare.

Pentru impementarea comenzii branch, am parcurs lista de branchuri a vcsului si
am verificat daca mai exista un branch cu acelasi nume. In cazul in care nu
exista, il cream; in caz contrar, afisam eroare.

Pentru comanda checkout am verificat urmatoarele cazuri:   
-daca erau elemente in staging, am semnalat eroarea corespunzatoare
-daca primeste id ul unui commit ca parametru: parcurg lista de commituri a
branchului curent si caut dupa id. Daca nu gasesc commitul cu id-ul respectiv,
afisez eroare. In cazul in care il gaseste, actualizez headul din vcs, setez
sistemul de fisiere din vcs cu cel al commitului si sterg commiturile de dupa.
-daca primeste un brach: parcurg lista de branchuri si caut branchul respecti
pentru a-l seta ca branch curent. Actualizez sistemul de fisiere din vcs cu cel
al branchului respectiv. Daca nu-l gasesc, returnez eroare.

In clasa LogOperation am parcurs lista de commituri a branchului curent si am
afisat id-ul si mesajul fiecaruia.

In clasa RollbackOperation am implementat comanda rollback: am golit stagingul
si am actualizat sistemul de fisiere cu cel al ultimului commit.

Pentru comanda status am afisat branchul curent si comenzile din staging, daca
existau.

Am creat clasa InvalidVcsOperation pentru a returna eroare in cazul gasirii unei
comenzi gresite de vcs.
