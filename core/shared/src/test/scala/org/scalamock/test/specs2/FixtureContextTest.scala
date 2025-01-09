// Copyright (c) 2011-2015 ScalaMock Contributors (https://github.com/paulbutcher/ScalaMock/graphs/contributors)
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

package org.scalamock.test.specs2

import org.scalamock.specs2.MockContext
import org.scalamock.test.mockable.TestTrait
import org.specs2.mutable.Specification

/**
 *  Tests for mocks defined in fixture-contexts
 *
 *  Tests for issue #25
 */
class FixtureContextTest extends Specification {

  trait TestSetup extends MockContext {
    val mockedTrait = mock[TestTrait]
    val input = 1
    val output = "one"
  }

  trait TestSetupWithExpectationsPredefined extends TestSetup {
    (mockedTrait.oneParamMethod).expects(input).returning(output)
  }

  trait TestSetupWithHandlerCalledDuringInitialization extends TestSetupWithExpectationsPredefined {
    mockedTrait.oneParamMethod(input) must_== output
  }

  "Specs2 suite" should {
    "allow to use mock defined in fixture-context" in new TestSetup {
      (mockedTrait.oneParamMethod).expects(input).returning(output)
      (mockedTrait.oneParamMethod).expects(2).returning("two")

      mockedTrait.oneParamMethod(input) must_== output
      mockedTrait.oneParamMethod(2) must_== "two"
    }

    "allow to use mock defined in fixture-context with expectations predefined" in new TestSetupWithExpectationsPredefined {
      (mockedTrait.oneParamMethod).expects(2).returning("two")

      mockedTrait.oneParamMethod(input) must_== output
      mockedTrait.oneParamMethod(2) must_== "two"
    }

    "allow mock defined in fixture-context to be used during context initialization" in new TestSetupWithHandlerCalledDuringInitialization {
      (mockedTrait.oneParamMethod).expects(2).returning("two")

      mockedTrait.oneParamMethod(2) must_== "two"
    }
  }
}