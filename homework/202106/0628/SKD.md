1. convert key into integer through hash function.
2. For the integer after hash function, add the value to the end of array corresponding to the key, or add the value to the end of linked list corresponding to the key.
3. if the number of element exceeds 0.75 of total capacity, double the total capacity and remap all the key value pair again.
4. search is O(1) in average
   add is O(1) in average
   delete is O(1) in average
Under concurrent situation, we may need to make add, delete and rehashing synchronized

Key needs to implement hashCode() function. And we get the entry through Map<K, V>.Entry

