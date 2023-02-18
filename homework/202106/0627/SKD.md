B tree                                                                                    B+ tree
data is stored in leaf nodes and internal nodes                                           Data is stored only in leaf nodes
Searching is slower because data is stored in both leaf and internal nodes                Searching is faster
No redundant search keys are present                                                      Redundant search keys may be present
Deletion is complex                                                                       Deletion is easy since data can be directly deleted from leaf nodes
Leaf nodes cannot be linked together                                                      Leaf nodes are linked together to form a linked list

Application: search for data in database that is stored in disk space.
