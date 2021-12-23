# OSI七层协议、TCP/IP五层协议

## OSI Model
Application - Presentation - Session - Transport - Network - Data Link - Physical

Application layer:
- End-user applications

Presentation layer:
- Present data, encoding
- Encryption

Session layer:
- manage user sessions
- a session is an exchange of information between local applications and remote services on other end systems.

Transport layer:
- Split large chunks of data to smaller chunks
- Add additional information (head, tailer), e.g., checksum

Network layer:
- Transport packets
- Routing
- Load Balancing

Data link layer:
- flow control
- error detection/correction
- Encapsulates packets, resolve transmission conflicts
- Multiplexing & Demultiplexing

Physical layer:
- Transmits bits

## TCP/IP Model
Application - Transport - Network - Data Link - Physical

Application layer contains: Application, Presentation, Session layers in OSI model.
