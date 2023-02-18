# inode #
inode stores the metadata for block, which is the basic storage unit of mainstream file systems. The metadata includes the size, timestamp, location, permission and link information.  
Each file has its own 'inode-number'. When reading or writing on a file, the system firstly get the 'inode-number' via the file's path. 
Then it discovers the location of the file using the metadata in the inode.
In other words, inode is the index for block. 

# hard link and soft link #
hard link - It means different filenames are linked to a single 'inode-number'. In such case, the link between each filename and the file is 'hard link'.  
soft link - It means file A's content is the path of file B. A is like a pointer to B. The link between A and B is 'soft link'.

# fd #
Fd(file descriptor) is a pointer to inode. Fd is thread level. Each time the user opens the same file, a different fd is generated from the file's path, then it's used to get the "inode-number". An inode can correspond to different fds.  
The default limitation of fd's number for each user is 1024. We can modify it by changing the related contents in /etc/security.
