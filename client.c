#include <stdio.h>
#include <windows.h>
#include <winsock.h>
#include <stdlib.h>
#include <string.h>
#define VETTSIZE 512

void ErrorHandler(char *errorMessage) {
printf(errorMessage);
}
void ClearWinSock() {
#if defined WIN32
WSACleanup();
#endif
}
int main(int argc, char *argv[]) {
#if defined WIN32
WSADATA wsaData;
int iResult = WSAStartup(MAKEWORD(2 ,2), &wsaData);
if (iResult != 0) {
	printf ("error at WSASturtup\n");
return EXIT_FAILURE;
}
#endif
int sock;
struct sockaddr_in ServAddr;
struct sockaddr_in fromAddr;
unsigned int fromSize;
char String[VETTSIZE];
char Buffer[VETTSIZE];
int StringLen;
int respStringLen;
// CREAZIONE DELLA SOCKET
if ((sock = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP)) < 0)
ErrorHandler("socket() failed");
// COSTRUZIONE DELL'INDIRIZZO DEL SERVER
int MAXIP;
char SName[VETTSIZE];
short PORTA;
char sIP[MAXIP];
struct hostent *Rem;
struct sockaddr_in sAdd;

//1) Il client legge da tastiera il nome dell’host e il numero di porta da utilizzare per contattare il Server//
puts("Inserisci il nome del server: ");
gets(SName);
//gethostbyname per ricavare il nome dell'host
Rem= gethostbyname(SName);
	printf("Nome trovato = %s\n", Rem->h_name);
	strcpy(sIP, inet_ntoa(*(struct in_addr*) Rem->h_addr_list[0]));
printf("L'ip del server e': '%s'\n\n", sIP);
puts("Inserisci la porta del server UDP a cui connetterti: ");
scanf("%hu", &PORTA); 
getchar();
memset(&ServAddr, 0, sizeof(ServAddr));
ServAddr.sin_family=AF_INET;
ServAddr.sin_port=htons(PORTA);
ServAddr.sin_addr.s_addr=inet_addr(sIP);

// 2) INVIO PRIMA STRINGA AL SERVER//
printf("Inserisci una stringa da inviare al server\n");
scanf("%s", String);
if ((StringLen = strlen(String)) > VETTSIZE)
	ErrorHandler("echo word too long");

if (sendto(sock, String, StringLen, 0, (struct
	sockaddr*)&ServAddr, sizeof(ServAddr)) != StringLen)
	ErrorHandler("sendto() sent different number of bytes than expected\n");

// 5) RICEZIONE "OK" //
fromSize = sizeof(fromAddr);
respStringLen = recvfrom(sock, Buffer, VETTSIZE, 0, (struct
sockaddr*)&fromAddr, &fromSize);
if (ServAddr.sin_addr.s_addr != fromAddr.sin_addr.s_addr){
	fprintf(stderr, "Error: received a packet from unknown source.\n");
	exit(EXIT_FAILURE);
}
Buffer[respStringLen] = '\0';
printf("Received: %s\n", Buffer);

// 6) INVIO SECONDA STRINGA //
char stringa[VETTSIZE];
int i;
int vett_size=0;
int p=0;
printf("inserisci la seconda stringa da inviare al server: \n");
scanf("%s", stringa);

//blocco per contare le vocali
for(i=0; i<strlen(stringa); i++){
	if ( stringa[i]=='a' || stringa[i]=='e' || stringa[i]=='i' || stringa[i]=='o' || stringa[i]=='u' ){
		vett_size++;
	}
}
printf("la stringa ha %d vocali \n", vett_size);
if (sendto(sock, (void*)&vett_size, sizeof(ServAddr), 0, (struct sockaddr*)&ServAddr, sizeof(ServAddr)) != sizeof(ServAddr))
	ErrorHandler("sendto() sent different number of bytes than expected\n");
else
	printf("Invio %d che corrisponde al numero di vocali della seconda stringa\n",vett_size);
//creazione di un nuovo vettore contenente solo le vocali
char vett[vett_size];
for(i=0; i<strlen(stringa); i++){
	if ( stringa[i]=='a' || stringa[i]=='e' || stringa[i]=='i' || stringa[i]=='o' || stringa[i]=='u' ){
		vett[p]=stringa[i];
		p++;
	}
}
//STAMPA LA STRINGA DI VOCALI
for(i=0;i<vett_size;i++){
	//INVIA LE VOCALI AL SERVER UNA PER VOLTA
	if (sendto(sock, &vett[i], sizeof(vett[i]), 0, (struct
	sockaddr*)&ServAddr, sizeof(ServAddr)) != sizeof(vett[i]))
	ErrorHandler("sendto() sent different number of bytes than expected\n");
}

//RITORNO VOCALI MAIUSCOLE SEPARATAMENTE

for(int j=0;j<vett_size;++j){
	fromSize = sizeof(fromAddr);
	respStringLen = recvfrom(sock, &vett[i], vett_size, 0, (struct
	sockaddr*)&fromAddr, &fromSize);
	if (ServAddr.sin_addr.s_addr != fromAddr.sin_addr.s_addr)
	{
		fprintf(stderr, "Error: received a packet from unknown source.\n");
		exit(EXIT_FAILURE);
	}
	// 8) VISUALIZZA VOCALI
	printf("Received: %s\n", &vett[i]);
}
// 9) Chiusura del client
Buffer[respStringLen] = '\0';
closesocket(sock);
ClearWinSock();
system("pause");
return EXIT_SUCCESS;
}
