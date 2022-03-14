# cmake和makefile的区别，简述cmake到可执行文件的过程
### cmake和makefile的区别
CMake是一个跨平台的软件，在很多平台可以使用。一般在windows下，我们会直接使用VS生成项目，在linux下面，我们也可以使用QT Creater生成项目，但是两个不同平台上面的项目不能相互移植。这就有了CMake的用武之地，我们可以先编写一个CMakeLists.txt文件，将需要的.h和.cpp文件包含进来，然后在不同的平台使用CMake调用各自的编译器生成各自的工程。  
  
Makefile则是linux下面的文件，对于一个包含很多文件的工程，如果直接编译，那么我们就需要使用一些命令将所有的文件都包括进来。如果我们对其中的一些文件稍做修改，那么我们需要重新输入这些命令。Makefile文件就可以很好的解决这个问题，它将所需要的命令都包含在这个Makefile文件中，然后简单的make一下就完成了所有的步骤。

### cmake到可执行文件的过程
1.编写名为CMakeList.txt的文件，与源代码文件（a.cpp）放在同一个文件夹。  
2.内容包含以下3行，意思依次为：
    
    i.设置cmake最低版本以及若版本被违反抛出的错误。
    ii.设置项目名称、语言（CXX代表C++）。
    iii.设置可执行文件的名称、源代码文件的文件名。
```
cmake_minimum_required(VERSION 3.5 FATAL_ERROR)
project(recipe-01 LANGUAGES CXX)
add_executable(a a.cpp)
```
3.执行 cmake ..
