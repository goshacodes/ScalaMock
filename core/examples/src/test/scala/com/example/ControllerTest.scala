// Copyright (c) 2011-2025 ScalaMock Contributors (https://github.com/ScalaMock/ScalaMock/graphs/contributors)
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package com.example

import org.scalamock.scalatest.MockFactory
import org.scalatest.funsuite.AnyFunSuite

import scala.math.{Pi, sqrt}
 
class ControllerTest extends AnyFunSuite with MockFactory {
 
  test("draw line") {
    val mockTurtle = mock[Turtle]
    val controller = new Controller(mockTurtle)
 
    inSequence {
      inAnyOrder {
        (() => mockTurtle.penUp()).expects().returns(())
        (() => mockTurtle.getPosition).expects().returning(0.0, 0.0)
        (() => mockTurtle.getAngle).expects().returning(0.0)
      }
      (mockTurtle.turn).expects(~(Pi / 4))
      (mockTurtle.forward).expects(~sqrt(2.0))
      (() => mockTurtle.getAngle).expects().returning(Pi / 4)
      (mockTurtle.turn).expects(~(-Pi / 4))
      (() => mockTurtle.penDown()).expects()
      (mockTurtle.forward).expects(1.0)
    }
 
    controller.drawLine((1.0, 1.0), (2.0, 1.0))
  }
}
