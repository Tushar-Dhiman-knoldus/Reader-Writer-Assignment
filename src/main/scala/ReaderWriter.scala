package com.knoldus.readerwriterproblem

import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantReadWriteLock

class WriterPreferenceLock {
  private val lock = new ReentrantReadWriteLock(true)

  def readLock(): Unit = lock.readLock().lock()

  def readUnlock(): Unit = lock.readLock().unlock()

  def writeLock(): Unit = lock.writeLock().lock()

  def writeUnlock(): Unit = lock.writeLock().unlock()
}

object ReaderWriter extends App {
  // Create a thread pool to execute the tasks concurrently
  private val executor = Executors.newFixedThreadPool(4)
  private val lock = new WriterPreferenceLock()
  private var sharedResource = List.empty[Int]

  // Define a function to perform a read operation on the shared resource
  private def readOperation(): Unit = {
    lock.readLock()
    try {
      println(s"Reader ${Thread.currentThread().getName} is reading the shared resource: $sharedResource")
      Thread.sleep(1000)
    } finally {
      println(s"Reader${Thread.currentThread().getName} is  unlocked")
      lock.readUnlock()
    }
  }

  // Define a function to perform a write operation on the shared resource
  private def writeOperation(): Unit = {
    lock.writeLock()
    try {
      println(s"Writer ${Thread.currentThread().getName} is modifying the shared resource")
      sharedResource = sharedResource :+ sharedResource.length
      Thread.sleep(1000)
    } finally {
      println(s"Writer ${Thread.currentThread().getName} is unlocked")
      lock.writeUnlock()
    }
  }

  // Submit tasks to the thread pool
  executor.submit(new Runnable() {
    def run(): Unit = readOperation()
  })
  executor.submit(new Runnable() {
    def run(): Unit = readOperation()
  })
  executor.submit(new Runnable() {
    def run(): Unit = writeOperation()
  })
  executor.submit(new Runnable() {
    def run(): Unit = readOperation()
  })
  executor.submit(new Runnable() {
    def run(): Unit = readOperation()
  })
  executor.submit(new Runnable() {
    def run(): Unit = writeOperation()
  })
  executor.submit(new Runnable() {
    def run(): Unit = writeOperation()
  })
  executor.submit(new Runnable() {
    def run(): Unit = readOperation()
  })

  // Shutdown the thread pool
  executor.shutdown()
}


