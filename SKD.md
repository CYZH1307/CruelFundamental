Step 1: client sends request to server
        1.1 Client generates random number R1 and sends it to server
        1.2 Client tells server which encryption algorithm it can support
     
Step 2: Server sends digital certificate to client
        2.1 Server generates random number R2
        2.2 Server picks encryption algorithm that both client and server support
        2.3 Server sends certificate, R2 encryption algorithm to client
    
Step 3: Client checks digital certificate
        3.1 Checks the reliability of certificate: if the certificate can be decrypted by CA's public key, then certificate is fine
        3.2 Checks the legalness of certificate
        3.3 get certificate's public key, encrption algorithm and R2, generates R3 from it.
        3.4 get session key from R1, R2, R3
        3.5 sends R3 to server
     
Step 4: Server decryptS R3 sent from client, and generate session key from R1, R2, R3.

Step 5: Client sends encrypted data to server
        Server decrypts data sent from client, and sends encrypted data to client
        Client decrypts data sent from server
