package org.scalamock.scalatest

import org.scalamock.clazz.Mock as MacroMock
import org.scalamock.proxy.ProxyMockFactory
import org.scalatest.TestSuite

import scala.reflect.ClassTag

/**
  * allows combining of macro mocks wih proxy mocks in the same Suite
  * {{{
  * val macroMock = mock[Foo]
  * val proxyMock = Proxy.mock[Bar]
  * }}}
  */
trait MixedMockFactory extends AbstractMockFactory with MacroMock { this: TestSuite =>

  object Proxy extends ProxyMockFactory {
    import org.scalamock.proxy.*
    def mock[T: ClassTag]: T & Mock = super.mock[T]
    def stub[T: ClassTag]: T & Stub = super.stub[T]
  }
}
