How observer pattern works?

Observer Pattern (is a software design pattern), responsible to command Subscriber to do something, synchronously. This is typically used when a stream of events happen with one to many relationship.

Design:
Observable Interface: attach():void, detach():void, notify():void     
Concrete Observable: getState():State, setState():void, state:State Subject -- has a list of observer objects, i.e. an iterator
Observer: update():void
Concrete Observer: observerState:State, update():void     Observer


Use case:
Turn on/off notifications on your iphone
Amazon: Notify me when product is available

Defects of observer pattern: 
1. subject and observer are loosely coupled
2. Observer interface can be initialized, thus no option for composition
3. Performance issues if observer is misused
4. NOtification can be unreliable and may result in race conditions or inconsistency
(https://medium.datadriveninvestor.com/design-patterns-a-quick-guide-to-observer-pattern-d0622145d6c2)

Benefits of observer pattern:
1. you don't want to use busy waiting and polling
2. Decouple the dependencies between subject objects and observers, following the open-closed principle
3. maintainable because the observable classes and their dependencies
4. follow the single responsibility 




