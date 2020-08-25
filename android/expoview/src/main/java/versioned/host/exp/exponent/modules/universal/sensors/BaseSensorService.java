package versioned.host.exp.exponent.modules.universal.sensors;

import android.hardware.SensorEventListener2;

import org.unimodules.core.interfaces.InternalModule;

import javax.inject.Inject;

import host.exp.exponent.di.NativeModuleDepsProvider;
import host.exp.exponent.kernel.ExperienceId;
import host.exp.exponent.kernel.services.ExpoKernelServiceRegistry;
import host.exp.exponent.kernel.services.sensors.SensorKernelServiceSubscription;
import host.exp.exponent.kernel.services.sensors.SubscribableSensorKernelService;

public abstract class BaseSensorService implements InternalModule {
  private ExperienceId mExperienceId;

  @Inject
  protected ExpoKernelServiceRegistry mKernelServiceRegistry;

  public BaseSensorService(ExperienceId experienceId) {
    mExperienceId = experienceId;
    NativeModuleDepsProvider.getInstance().inject(BaseSensorService.class, this);
  }

  protected ExperienceId getExperienceId() {
    return mExperienceId;
  }

  protected ExpoKernelServiceRegistry getKernelServiceRegistry() {
    return mKernelServiceRegistry;
  }

  protected abstract SubscribableSensorKernelService getSensorKernelService();

  public SensorSubscription createSubscriptionForListener(SensorEventListener2 sensorEventListener) {
    ScopedSensorEventListener scopedSensorEventListener = new ScopedSensorEventListener(sensorEventListener);
    SensorKernelServiceSubscription sensorKernelServiceSubscription = getSensorKernelService().createSubscriptionForListener(getExperienceId(), scopedSensorEventListener);
    return new SensorSubscription(sensorKernelServiceSubscription);
  }
}
