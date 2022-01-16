# Put 和 Delete区别

PUT：

- PUT puts a file or resource at a specific URI, and exactly at that URI. If there's already a file or resource at that URI, PUT replaces that file or resource. If there is no file or resource there, PUT creates one. 

- Create or Modify a resource. 
- PUT responses are not cacheable.
- Idempotent: No change in resource status if requested many times 多操作几次并不会影响结果
- On successful resource creation, HTTP success code 201(Created)

DELETE：

- Delete a resource identified by a URL.
- Idempotent
- On successful deletion of record, we can see 200 (OK) or 204 (No Content).
