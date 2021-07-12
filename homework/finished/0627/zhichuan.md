# Btree vs B+tree

> https://www.geeksforgeeks.org/difference-between-b-tree-and-b-tree/
> https://www.baeldung.com/cs/b-trees-vs-btrees

## Btree

B-tree is self-balancing tree as nodes are sorted inorder traversal

- all the leaf nodes of the B-tree must be at the same level
- above the leaf nodes of the b-tree, there should be no empty sub-trees
- B-tree's height should lie as low as possible
- each node takes up a disk page
- the nodes in a B-tree of order m can have maximum of m children

## B+tree

- all leaf nodes are linked together in a doubly-linked list
- data is stored on the leaf nodes only. Internal nodes only hold keys and act as routers to the correct leaf node

## Conclusion

- B+tree's search key can be repeated
- it's easier to delete data in b+tree
most db use B+tree
