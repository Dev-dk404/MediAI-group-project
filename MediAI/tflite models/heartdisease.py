# -*- coding: utf-8 -*-
"""heartDisease.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1xg37MGBJOo6OIUEEOFgX4Otp2_mvAMtP
"""

import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import train_test_split
import keras
from keras.models import Sequential
from keras.layers import Dense, Dropout, Activation
from keras.callbacks import ModelCheckpoint
import tensorflow as tf

df = pd.read_csv("cleveland.csv")

df.columns = ['age', 'sex', 'cp', 'trestbps', 'chol',
              'fbs', 'restecg', 'thalach', 'exang',
              'oldpeak', 'slope', 'ca', 'thal', 'target']
df['target'] = df.target.map({0: 0, 1: 1, 2: 1, 3: 1, 4: 1})

# mapping sex where 1= male and 0 = female

# df['sex'] = df.sex.map({0: 'female', 1:'male'})

# filling null values with mean values
df = df.fillna(df.mean())
X = df.drop('target', axis=1)
y = df['target']

X_train, X_test, y_train, y_test = train_test_split(X, y, random_state=1, test_size=0.2)
X_train.shape, y_train.shape,X_test.shape,y_test.shape

model = Sequential()
model.add(Dense(13, activation = 'relu'))

model.add(Dense(11, activation='relu'))

model.add(Dense(8, activation='relu'))

model.add(Dense(4, activation='relu')) 

model.add(Dense(1, activation='sigmoid')) 
model.build(input_shape=(241,13))
model.summary()

# model = DecisionTreeRegressor(random_state=1)
model.compile(optimizer='adam',loss='binary_crossentropy',metrics=['acc'])

model.compile(optimizer='adam',loss='binary_crossentropy',metrics=['acc'])

checkpointer = ModelCheckpoint('heart_disease.h5', monitor='val_acc', mode='max', verbose=2, save_best_only=True)

history=model.fit(X_train, y_train, batch_size=16, epochs=350, validation_data=(X_test, y_test), callbacks=[checkpointer])

present_model = keras.models.load_model('heart_disease.h5')

print("Accuracy of our model on test data : " , present_model.evaluate(X_test,y_test)[1]*100, "%")

converter = tf.lite.TFLiteConverter.from_keras_model(present_model) # path to the SavedModel directory
converter.target_spec.supported_ops = [
  tf.lite.OpsSet.TFLITE_BUILTINS, # enable TensorFlow Lite ops.
  tf.lite.OpsSet.SELECT_TF_OPS # enable TensorFlow ops.
]

tflite_model = converter.convert()

with open('heart_disease.tflite','wb') as f:
  f.write(tflite_model)