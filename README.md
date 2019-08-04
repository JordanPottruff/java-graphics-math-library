# Java Graphics Math Library
**Author**: Jordan Pottruff

The Java Graphics Math Library (JGML) encapsulates important graphics-related mathematics into
various matrix and vector implementations. This makes it easier to program graphics-related software
in the Java language.

## Overview
### Design
![UML Diagram](img/uml-diagram.png)

JGML provides multiple implementations of both vectors and matrices. These implementations primarily
differ by how the dimensions of the object are defined. While highly generalized classes like 
`MatMN` and `VecN` could be exclusively used, certain functionality requires strict guarantees about
the exact dimension of the vector or matrix. Therefore, various dimension-specific classes exist 
within an intuitive inheritance structure.

The primary advantage of this library is the guarantee it provides about object immutability. All 
vector and matrix classes have *deep immutability*, making them safe for use in concurrent 
programming. In addition, mutable versions are available in some classes using the *builder 
pattern*. 

### Vectors
The bare minimum requirements for a vector object are specified by the `Vec` interface. This 
interface describes the basic functionality of vector objects, such as methods for getting the 
magnitude, computing the normalized form, and performing addition, multiplication (dot product), 
etc.

The `VecN` class represents a generalized vector. That is, a vector with some dimension N. The only
hard limit placed on this generalized implementation is that the dimension be at least 2. This 
class meets the bare minimum requirements defined by the `Vec` interface.

The `Vec2`, `Vec3`, and `Vec4` classes are the dimension-specific vector definitions, and are 
subclasses of `VecN`. The most important benefit of using these specific vector classes is that
their overridden operations (methods) return a more specific class. For example, `VecN`'s dot 
product method, `multiply(Vec vec)`, returns a `VecN` object. However, `Vec4` overrides this 
method to return the product as a `Vec4`, since the result of a successful addition with a 
4-dimensional vector has to return a 4-dimensional vector as well. 

Some methods, such as accessors to the x, y, z, etc coordinates or the cross product operations 
only apply to vectors of a certain dimension. Therefore, it is recommended that you use the most
specific vector type that you can in order to provide as much flexibility as possible.

### Matrices
The bare minimum requirements for a matrix object are specified by the `Mat` interface. This 
interface describes the basic functionality of matrix objects, such as methods for getting the 
columns/row vectors or operations like addition, multiplication, etc.

The `MatMN` class represents the most generalized matrix possible. Specifically, it represents a 
matrix of some dimension M by N. Similar to `VecN`, the only hard limit imposed on these dimensions
is that they be at least 2. This class meets the bare requirements defined by the `Mat` interface.

The `MatN` class is a subclass of `MatMN` and represents a generalized square matrix of dimension N 
by N. It provides the implementation of operations that are specific to square matrices, such as 
calculating the determinant or inverse matrix.

The `Mat2`, `Mat3`, and `Mat4` classes are the dimension-specific matrix definitions, and are 
subclasses of `MatN`. Like the dimension-specific vectors, these provide useful overrides of 
`MatN` methods that modify the return type to use dimension-specific matrix classes. 

#### Transformations

Square matrices are incredibly common in graphical applications. Often, they are used to represent
*affine transformations* in different dimensions. To make it easier to build useful transformations,
each dimension-specific class provides a `TransformBuilder` inner-class that constructs a matrix
out of a sequence of rotation, scaling, translation, and shearing transformations. 

## Examples

