import joblib
import pandas as pd
loaded_model = joblib.load('HeartDiseaseFinalizedModel.sav')
df = pd.read_csv('cleveland.csv')
df.columns = ['age', 'sex', 'cp', 'trestbps', 'chol',
              'fbs', 'restecg', 'thalach', 'exang',
              'oldpeak', 'slope', 'ca', 'thal', 'target']
df['target'] = df.target.map({0: 0, 1: 1, 2: 1, 3: 1, 4: 1})
df = df.fillna(df.mean())
X = df.drop('target', axis=1)
y = df['target']


result = loaded_model.score(X,y)
print(result)
