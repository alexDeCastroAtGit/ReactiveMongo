/*
 * Copyright 2013 Stephane Godbillon
 * @sgodbillon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package reactivemongo.bson.netty

import java.nio.ByteOrder._
import reactivemongo.bson.{ ReadableBuffer, WritableBuffer }
import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.buffer.ChannelBuffers

class ChannelBufferReadableBuffer(protected val buffer: ChannelBuffer) extends ReadableBuffer {
  def size = buffer.capacity()

  def index = buffer.readerIndex()

  def discard(n: Int) = {
    buffer.readerIndex(buffer.readerIndex + n - 1)
  }

  def slice(n: Int) = {
    val b2 = buffer.slice(buffer.readerIndex(), n)
    new ChannelBufferReadableBuffer(b2)
  }

  def readBytes(array: Array[Byte]): Unit = {
    buffer.readBytes(array)
  }

  def readByte() = buffer.readByte()

  def readInt() = buffer.readInt()

  def readLong() = buffer.readLong()

  def readDouble() = buffer.readDouble()

  def readable() = buffer.readableBytes()
}

object ChannelBufferReadableBuffer {
  def apply(buffer: ChannelBuffer) = new ChannelBufferReadableBuffer(buffer)
}

class ChannelBufferWritableBuffer extends WritableBuffer {
  val buffer = ChannelBuffers.dynamicBuffer(LITTLE_ENDIAN, 32)

  def index = buffer.writerIndex

  def setInt(index: Int, value: Int) = {
    buffer.setInt(index, value)
    this
  }

  def writeBytes(array: Array[Byte]): WritableBuffer = {
    buffer writeBytes array
    this
  }

  def writeByte(byte: Byte): WritableBuffer = {
    buffer writeByte byte
    this
  }

  def writeInt(int: Int): WritableBuffer = {
    buffer writeInt int
    this
  }

  def writeLong(long: Long): WritableBuffer = {
    buffer writeLong long
    this
  }

  def writeDouble(double: Double): WritableBuffer = {
    buffer writeDouble double
    this
  }
}

object ChannelBufferWritableBuffer {
  def apply() = new ChannelBufferWritableBuffer()
}