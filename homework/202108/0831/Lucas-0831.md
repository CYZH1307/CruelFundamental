# 策略模式
- https://www.runoob.com/design-pattern/strategy-pattern.html

## 原理
- 定义多种算法，可以切换
- 主要解决 if ... else ... 难以维护的问题
- 把算法封装成类，实现同一接口

## 实现
- interface Strategy {
-   int operate(int a, int b);
- }
- 
- class Addition implements Strategy {
-   @Override
-   int operate(int a, int b) {
-     return a + b;
-   }
- }
- 
- class Substraction {
- }
- 
- class Multiplication {
- }
- 
- class Devision {
- }
- 
- class Context {
-   private Strategy strategy;
-   public Context(Stragegy strategy) {
-     this.strategy = strategy;
-   }
-   public int execute(int a, int b) {
-     return strategy.operate(a, b);
-   }
- }
- Context c1 = new Context(new Addition());
- c1.execute(1, 5);
- Context c2 = new Context(new Multiplication());
- c2.execute(1, 5);
