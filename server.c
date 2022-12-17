#include <winsock.h>
#include <stdlib.h>
#include <string.h> /* for memset() */
#define VETTSIZE 512
#define PORT 27015
void ErrorHandler(char *errorMessage) {
printf(errorMessage);
}
void ClearWinSock() {
#if defined WIN32
WSACleanup();
#endif
}
int main(int argc, char **argv) {
#if defined WIN32
WSADATA wsaData;
int iResult = WSAStartup(MAKEWORD(2 ,2), &wsaData);
if (iResult != 0) {
	printf("error at WSASturtup\n");
return EXIT_FAILURE;
}
#endif
int sock;
struct sockaddr_in ServAddr;
struct sockaddr_in ClntAddr;
int cliAddrLen;
char Buffer[VETTSIZE];
int recvMsgSize;
int recvMsgSize2;
char risposta[VETTSIZE]="OK";
char stringaVocali[VETTSIZE];
int vett;
int vett_size;
// CREAZIONE DELLA SOCKET
if ((sock = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP)) < 0)
	ErrorHandler("socket() failed");
// COSTRUZIONE DELL'INDIRIZZO DEL SERVER
memset(&ServAddr, 0, sizeof(ServAddr));
ServAddr.sin_family = AF_INET;
ServAddr.sin_port = htons(PORT);
ServAddr.sin_addr.s_addr = inet_addr("127.0.0.1");
struct sockaddr_in  client;
struct hostent* host;
int fd, len;
len = sizeof( client );
fd  = accept( sock, (struct sockaddr*)&client, &len );
//gethostbyaddr per ricavare l'indirizzo dell'host
host = gethostbyaddr( (const void*)&client.sin_addr, 
        sizeof(struct in_addr), AF_INET );
// BIND DELLA SOCKET
if ((bind(sock, (struct sockaddr *)&ServAddr, sizeof(ServAddr))) < 0)
	ErrorHandler("bind() failed");

// 3) RICEZIONE DELLA PRIMA STRINGA DAL CLIENT //
while(1) {
	cliAddrLen = sizeof(ClntAddr);
	recvMsgSize = recvfrom(sock, Buffer, VETTSIZE, 0, (struct
	sockaddr*)&ClntAddr, &cliAddrLen);
	
	printf("Handling client %s\n", inet_ntoa(ClntAddr.sin_addr));
	printf("%s ricevuto dal client con nomehost %s\n ", Buffer,host->h_name);
	
	// 4) INVIA OK AL CLIENT //
	if (sendto(sock, risposta, recvMsgSize, 0, (struct sockaddr *)&ClntAddr, 
	sizeof(ClntAddr)) != recvMsgSize)
	ErrorHandler("sendto() sent different number of bytes than expected");
	cliAddrLen = sizeof(ClntAddr);
	
	memset(Buffer, 0, sizeof(Buffer));
		
	//RICEZIONE DEL NUMERO DELLE VOCALI DELLA STRINGA//	
	recvMsgSize = recvfrom(sock,(void*)&vett_size,sizeof(vett_size),0, (struct sockaddr*)&ClntAddr, &cliAddrLen);
	printf("Ricevo %d che corrisponde al numero delle vocali\n", vett_size);
	
	//RICEZIONE DELLE VOCALI UNA PER VOLTA//
	for(int i=0;i<vett_size;++i){
		recvMsgSize2 = recvfrom(sock, &Buffer[i], VETTSIZE, 0, (struct
		sockaddr*)&ClntAddr, &cliAddrLen);
		printf("RICEVO LE VOCALI %s\n", &Buffer[i]);
	}
	
	//CONVERSIONE LE VOCALI IN MAIUSCOLO	
	for(int i=0; Buffer[i]!=0 ;i++){
		Buffer[i]=toupper(Buffer[i]);
	}
	// 7) RINVIA LE VOCALI IN MAIUSCOLO //	
	for(int i=0;i<vett_size;++i){
		if (sendto(sock, &Buffer[i], sizeof(Buffer[i]), 0, (struct sockaddr *)&ClntAddr, 
		sizeof(ClntAddr)) != sizeof(Buffer[i]))
		ErrorHandler("sendto() sent different number of bytes than expected");
	}
	// 10) IN ATTESA DI ALTRI PACCHETTI//
	printf("In attesa di altri pacchetti...");
 }//fine ciclo while

}
