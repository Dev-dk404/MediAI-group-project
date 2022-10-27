import keras.models
import numpy as np
from numpy import loadtxt
import pandas as pd
from keras.models import Sequential
from keras.layers import Dense
from keras.callbacks import ModelCheckpoint

#loading dataset

df = pd.read_csv('pima-indians-diabetes.csv')
#df.info()
#dropping duplicate values
df.drop_duplicates(inplace=True)
#replacing 0s with Nan where true
columns = ['Glucose', 'BloodPressure', 'SkinThickness', 'Insulin', 'BMI']

for col in columns:
    df[col].replace(0, np.NaN, inplace=True)

#df.info()

#filling nan values with mean values
df = df.fillna(df.mean())

##splitting input (X) and output(Y) variables

X = df.drop('Outcome', axis=1)
y = df['Outcome']

#defining keras model
model = Sequential()
model.add(Dense(12,input_dim=8,activation='relu'))
model.add(Dense(8,activation='relu'))
model.add(Dense(1,activation='sigmoid'))

#compiling the keras model
model.compile(loss='binary_crossentropy',optimizer='adam',metrics=['accuracy'])

#creating a checkpoint to use the model later
#checkpointer = ModelCheckpoint('DiabetesModel.h5', monitor='val_acc' , verbose=2, save_best_only=True)

#fitting the keras model on dataset
model.fit(X,y,epochs=150,batch_size=10)
#evaluate the model

scores = model.evaluate(X,y,verbose=0)
print("%s: %.2f%%" % (model.metrics_names[1], scores[1]*100))

#saving the model to a single file for later use

model.save("Diabetes model.h5")
print("model saved")


##What's left
##Prediting and showing result


# for i in range(5):
#     print('%s => %d (expected %d)' % (X[i].tolist(), predictions[i], y[i]))
#
