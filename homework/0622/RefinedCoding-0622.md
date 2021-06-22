# 0622 - Object Oriented Characteristics

## Encapsulation
- class & interface
- visibility: public, protected & private
- advantage: easy to understand, test & maintain the components

## Inheritance
- BaseClass & ChildClass
- move common code to base class
- reuse the common code
- extend base class

## Polymorphism
- overloading
- virtual base method
- base destructor is always virtual

## Example
```cpp
class Vehicle {
protected:
  virtual void run() {
    cout << "0 mph" << endl;
  }
}

class Car : class Vehicle {
public:
  void run() {
    cout << "60 mph" <<endl;
  }
}

class Bike : class Vehicle {
public:
  void run() {
    cout << "10 mph" <<endl;
  }
}


int main(int argc, char** argv) {
  Vehicle* v = new Car();
  v.run();
  v = new Bike();
  v.run();
  
  return 0;
}
```
