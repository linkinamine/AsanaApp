package com.mohamedelaminebenallouch.asana.core.schedulers

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun io(): Scheduler
}