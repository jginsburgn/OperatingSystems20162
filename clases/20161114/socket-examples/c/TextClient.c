#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>

#define  DIRSIZE    1024   /* longitud maxima parametro entrada/salida */
#define  TAMABUFF   1024    //tamano del buffer

main(argc, argv)
   int    argc;  
   char  *argv[];
{
	int	                 puerto;
        int pid;
	int               sd,i;		   /* descriptores de sockets    */
	struct hostent 	   *hp;		   /* estructura del host        */
	struct sockaddr_in sin, pin; 	   /* direcciones socket        */
        int                *status;        /* regreso llamada sistema */
        char               *host;          /* nombre del host */
	
/* verificando el paso de parametros */

        if ( argc != 3) {
           fprintf(stderr,"Error uso: %s <host> <puerto> \n",argv[0]);
           exit(1);
        } 
        host = argv[1];
	puerto = atoi(argv[2]);


/* encontrando todo lo referente acerca de la maquina host */

	if ( (hp = gethostbyname(host)) == 0) {
		perror("gethosbyname");
		exit(1);
	}
		
/* llenar la estructura de direcciones con la informacion del host */

	pin.sin_family = AF_INET;
	pin.sin_addr.s_addr = ((struct in_addr *) (hp->h_addr))->s_addr;
	pin.sin_port = htons(puerto);                    

/* obtencion de un socket tipo internet */

	if ( (sd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
		perror("socket");
		exit(1);
	}

/* conectandose al PUERTO en el HOST  */

	if ( connect(sd, (struct sockaddr *)&pin, sizeof(pin)) == -1) {
		perror("connect");
		exit(1);
	}

    if ((pid = fork()) == -1) //generando proceso hijo
    {
      perror("error en el fork");
      exit(1);
    }

  if (pid == 0)
  {

     while(1){ // Se envia la peticion del servicio 
        char *mensaje ="hola sockets";

        if ((pid = send(sd, mensaje, TAMABUFF,0 )) == -1) //enviando
        {
          exit(1);
        }
        if (pid < TAMABUFF)
          break;
        //verificando envio en pantalla
        printf("\nel proceso hijo murio\n");//matando al hijo
        close(sd); //cerrar socket
        exit(0);

      }
  } // if pid
}
