### Java: 既然有了字节流,为什么还要有字符流?

字节流和字符流适用处理对象不同，字节流不适合处理字符串，字符流适合处理字符串。并且因为编码长度是不一样的，字节流不方便转化不同类型长度的字符，字节流转为字符流还要依靠不同编码方式，例如ASCII码里面一个英文字符为1个字节，一个中文字符为2个字节，但是到了UTF-8一个英文字符为1个字节，但是中文为3个字节。如果处理的单位就是字符串，通过字节流传递，转换为字符容易处理，并且很耗时。因此为了满足直接获取字符流的需求，才有了字符流。